package report

import anorm.{Macro, NamedParameter, ParameterValue, RowParser, SqlParser, SqlQueryResult}
import anorm.SqlParser._

import com.typesafe.scalalogging.LazyLogging
import play.api.Configuration
import play.api.libs.json.Json

import java.nio.file.Path
import java.sql.{ResultSet, Types}
import java.util.Date
import javax.inject.Inject
import javax.swing.tree.AbstractLayoutCache.NodeDimensions
import scala.util.{Failure, Success, Try}

case class Report(
  id: Option[String],
  name: Option[String],
  frame: Option[String],
  bootstrap: Option[String],
  lag: Option[String],
  query: Option[String],
  dimensions: Seq[(String, String)],
  metrics: Seq[(String, String)],
  partition: Option[String],
  requires: List[String],
  primary_index: List[String]
)

object Report {
  val parser: RowParser[Report] = Macro.namedParser[Report]
}

case class Job(
  id: String,
  report_id: Long,
  status: String,
  attempts: Int,
  aggr_start: Date,
  aggr_end: Date,
  queued_at: Date,
  executed_at: Date,
  completed_at: Date,
  records: Int,
  fail: String
)

object Job {
  val parser: RowParser[Job] = Macro.namedParser[Job]
}

case class JobReport(id: String, start: String, end: String, name: String, query: String, part: String)

object JobReport {
  val parser: RowParser[JobReport] = Macro.namedParser[JobReport]
}

class ReportService @Inject()(
  datasource: Datasource,
  warehouse: Warehouse,
  config: AppConfig,
  yamlLoader: YamlLoader
) extends LazyLogging {

  val logging = logger
  val app_path = config.APP_PATH
  val report_path = config.PATH_REPORTS
  val evolutions_path = config.PATH_EVOLUTIONS
  val composite_path = config.PATH_COMPOSITES

  def queue_jobs() = {

    logging.info("enqueuing new jobs")
    val count = datasource.sql_count(SqlValue.queue_jobs)
    logging.info("queued %s jobs", count)
    count
  }

  def disable_failing_reports() = {

    """Based on recently failed jobs, determine which reports should be disabled and disable them."""

    val failing_window = config.REPORT_DISABLE_WINDOW
    val disable_threshold = config.REPORT_DISABLE_THRESHOLD

    // reports which had at least {thresh} failures in the last {window} amount of time
    val to_disable = datasource.sql_fetchall(
      SqlValue.to_disable,
      List(NamedParameter("window", failing_window), NamedParameter("thresh", disable_threshold))
    )

    var count = 0
    for (reportRs <- to_disable) {
      val report = SqlQueryResult(resource.managed(reportRs)).as(Report.parser.*).head
      logger.warn("disabling report due to excessive failures=  %s", report)
      count += datasource.sql_count(
        SqlValue.fail_report,
        List(NamedParameter("rid", report.id))
      )
    }
    to_disable
  }

  def disable_report(report_id: String) = datasource.sql_count(
    SqlValue.disable_report,
    List(NamedParameter("rid", report_id))
  )

  def enable_report(report_id: String) = datasource.sql_count(
    SqlValue.enable_report,
    List(NamedParameter("rid", report_id))
  )

  def fetch_job(): Option[JobReport] = {
    """Fetch the first job that needs to run, and mark it as running. Return job row
    joined with its associated report."""
    var job = Option.empty[JobReport]

    // jobs that need to run (might be empty)
    val candidates = datasource.sql_fetchall(
      SqlValue.job_candidates
    )
    import scala.util.control.Breaks._
    breakable{
      for (candidateRs <- candidates) {
        val candidateId = SqlQueryResult(resource.managed(candidateRs)).as(str(1).*).head
        val count = datasource.sql_count(
          SqlValue.update_submitted_job,
          List(NamedParameter("job_id", candidateId))
        )
        // got it, get the extended info
        if (count > 0) {
          job = datasource.sql_fetchone(
            SqlValue.fetch_job_report,
            List(NamedParameter("job_id", candidateId)),
            Some(JobReport.parser)
          ).asInstanceOf[Option[JobReport]]
          break
        }
      }
    }
    // grab the job. opportunistically locked, only claimed if still in SUBMITTED state
    job
  }

  def do_job() = {
    val jobO = fetch_job()
    jobO match {
      case Some(job) =>
        Try {
          val num_records = cache_report_attempt(job)
          val count = num_records.sum
          datasource.sql_count(
            SqlValue.complete_job,
            List(NamedParameter("job_id", job.id), NamedParameter("records", count))
          )
        } match {
          case Failure(ex) => logger.warn(s"job $job failed (${ex})")
            Some(datasource.sql_count(
              SqlValue.fail_job,
              List(NamedParameter("job_id", job.id), NamedParameter("fail", ex.getMessage))
            ))
          case Success(x) => Some(x)
        }
      case _ => None
    }

  }

  def getSpec(rs: ResultSet): ParameterValue = {
    val columns = rs.getMetaData.getColumnCount
    val paramters = Range(0, columns).map { x =>
      val typedColumn = rs.getMetaData.getColumnType(x)
      val columnValue = typedColumn match {
        case Types.ARRAY => rs.getArray(x)
        case Types.BIGINT => rs.getBigDecimal(x)
        case Types.BINARY => rs.getBoolean(x)
        case Types.BIT => rs.getBoolean(x)
        case Types.BLOB => rs.getBytes(x)
        case Types.BOOLEAN => rs.getBoolean(x)
        case Types.CHAR => rs.getString(x)
        case Types.CLOB => rs.getString(x)
        case Types.DATALINK => rs.getBytes(x)
        case Types.DATE => rs.getDate(x)
        case Types.DECIMAL => rs.getBigDecimal(x)
        case Types.DISTINCT => rs.getString(x)
        case Types.DOUBLE => rs.getDouble(x)
        case Types.FLOAT => rs.getDouble(x)
        case Types.INTEGER => rs.getInt(x)
        case Types.JAVA_OBJECT => rs.getArray(x)
        case Types.LONGNVARCHAR => rs.getArray(x)
        case Types.LONGVARBINARY => rs.getArray(x)
        case Types.LONGVARCHAR => rs.getString(x)
        case Types.NCHAR => rs.getString(x)
        case Types.NCLOB => rs.getString(x)
        case Types.NULL => rs.getArray(x)
        case Types.NUMERIC => rs.getArray(x)
        case Types.NVARCHAR => rs.getArray(x)
        case Types.OTHER => rs.getArray(x)
        case Types.REAL => rs.getArray(x)
        case Types.REF => rs.getArray(x)
        case Types.REF_CURSOR => rs.getArray(x)
        case Types.ROWID => rs.getArray(x)
        case Types.SMALLINT => rs.getArray(x)
        case Types.SQLXML => rs.getArray(x)
        case Types.STRUCT => rs.getArray(x)
        case Types.TIME => rs.getTime(x)
        case Types.TIME_WITH_TIMEZONE => rs.getTime(x)
        case Types.TIMESTAMP => rs.getTimestamp(x)
        case Types.TIMESTAMP_WITH_TIMEZONE => rs.getTimestamp(x)
        case Types.TINYINT => rs.getInt(x)
        case Types.VARBINARY => rs.getBoolean(x)
        case Types.VARCHAR => rs.getString(x)
      }
      NamedParameter(rs.getMetaData.getColumnName(x), columnValue.toString).value
    }
    ParameterValue.toParameterValue(paramters.mkString(","))
  }

  def getSpec2(rs: ResultSet): ParameterValue = {
    val columns = rs.getMetaData.getColumnCount
    val ranged = Range(0, columns)
    val paramters = ranged.map { x =>
      "'%s',"
    }.mkString(",")
    paramters.format(ranged.map(rs.getObject(_).toString): _*)
  }

  def cache_report_attempt(jobReport: JobReport) = {

    val frame = s"${jobReport.part} >= '${jobReport.start}' AND ${jobReport.part} < '${jobReport.end}'"
    val start_datetime = s"cast('${jobReport.start}' as timestamp)"
    val end_datetime = s"cast('${jobReport.end}' as timestamp)"

    val table = "report_" + jobReport.name

    val total_inserts = 0
    warehouse.fetch_pages(
      jobReport.query,
      List(
        NamedParameter("framed", frame),
        NamedParameter("start_datetime", start_datetime),
        NamedParameter("end_datetime", end_datetime)
      )
    )(
      { case rsSets: Iterator[Page] => rsSets.flatMap( x =>
        x.resultSets.map(
          rs => datasource.execute_insert(
            SqlValue.replace_table_values,
            List(
              NamedParameter("job_id", jobReport.id),
              NamedParameter("table", jobReport.name),
              NamedParameter("spec", getSpec2(rs))
            )
          )
        )
      ).filter(_.isDefined).map(_ => 1)
      }
    )
  }

  def get_report(report_id_or_name: String) = {
    datasource.sql_fetchone(SqlValue.get_report, List(NamedParameter("rid", report_id_or_name)), Some(Report.parser)).asInstanceOf[Option[Report]]
  }

  def get_report_details(report_id_or_name: String) = {
    get_report(report_id_or_name) match {
      case Some(report) =>
        val requires = datasource.sql_fetchall(
              SqlValue.get_required,
              List(NamedParameter("rid", report_id_or_name))
        ).as(IdAndName.parser.*)

        val metrics =
            datasource.sql_fetchall(
              SqlValue.metrics,
              List(NamedParameter("rid", report_id_or_name))
        ).as(Metrics.parser.*)
        val dimensions =
            datasource.sql_fetchall(
              SqlValue.dimensions,
              List(NamedParameter("rid", report_id_or_name))
        ).as(Dimensions.parser.*)
        JobDetails(report, requires, metrics, dimensions)
    }
  }

  def get_reports(cursor: String, limit: Int) = {
    datasource.sql_fetchone(SqlValue.get_report_cursor, List(NamedParameter("cursor", cursor), NamedParameter("limit", limit)), Some(Report.parser)).asInstanceOf[Option[Report]]
  }

  def autoload_reports(path: String, name: Option[String] = None) = {
    val reports = yamlLoader.load_yaml_from_path[Report](path).map(_.asOpt).filter(_.isDefined).map(_.get)
    val nonNamedReports = reports.filter(x => name.isDefined && x.name != name)
    val defferedReprots = reports.filter(x => !(name.isDefined && x.name != name)).filter(!_.requires.isEmpty)
    val otherReports = reports.filter(y => defferedReprots.exists(x => x == y) && nonNamedReports.exists(x => x == y))

    val toLoadReports: List[Report] = otherReports.:::(defferedReprots)
    toLoadReports.map(x => Try(create_report_from_yaml(x)))
  }

  def disable_missing_reports(path: String) = {
    val rPath = yamlLoader.load_yaml_from_path[Report](path).map(_.asOpt).filter(_.isDefined).map(_.get)
    val local_report_names = if(rPath.isEmpty ) "''" else rPath.mkString(",")
    datasource.execute_update(SqlValue.update_active_report, List(NamedParameter("name", local_report_names)))
    datasource.execute_update(SqlValue.update_retired_report, List(NamedParameter("name", local_report_names)))

  }

  def create_report_from_yaml(report: Report) = {
    create_report(report.name.getOrElse(""), report.frame.getOrElse(""), report.bootstrap.getOrElse(""),report.lag.getOrElse(""),report.query.getOrElse(""), report.dimensions, report.metrics, report.partition.getOrElse(""), report.requires, report.primary_index.headOption )
  }

  def create_report(name: String, frame: String, bootstrap: String, lag: String, query: String, dimensions: Seq[(String, String)], metrics: Seq[(String, String)], partition: String, requires: List[String]=List.empty,
    primary_index: Option[String]=None) = {
    datasource.execute_insert(SqlValue.insert_report, List(NamedParameter("name", name), NamedParameter("frame", frame), NamedParameter("bootstrap", bootstrap), NamedParameter("lag", lag),
      NamedParameter("query", query), NamedParameter("partition", partition)))
    val report_id = datasource.sql_fetchone(SqlValue.last_id, List.empty).map(_.toString).getOrElse("")

    dimensions.foreach( x => datasource.execute_insert(SqlValue.dimensions, List(NamedParameter("report_id", report_id), NamedParameter("name", x._1), NamedParameter("type", x._2))))
    metrics.foreach( x => datasource.execute_insert(SqlValue.dimensions, List(NamedParameter("report_id", report_id), NamedParameter("name", x._1), NamedParameter("type", x._2))))

    val primary_index_fields = get_primary_index(partition, dimensions, primary_index)

    requires.foreach(requirement => datasource.execute_insert(SqlValue.requirement, List(NamedParameter("rid", report_id), NamedParameter("req", requirement))))

  }

  def get_primary_index(partition: String, dimensions: Seq[(String, String)], requested_index: Option[String]=None) = {
    requested_index match {
      case Some(index) => if(Seq(partition).++(dimensions.map(_._1)).exists(_ == index)) index else throw new RuntimeException("overriding primary index should contain all dimensions and the partition")
      case _ => Seq(partition).++(dimensions.map(_._1))
    }
  }

  def all_reports() = datasource.sql_fetchall(SqlValue.all_reports).as(Report.parser.*)

  def all_dependent_reports() = datasource.sql_fetchall(SqlValue.all_dependent_reports).as(Report.parser.*)

  def delete_report(report_id_or_name: String) = {
    val report = get_report(report_id_or_name)
    report match {
      case Some(r) =>
        val table = report_table(r.name.getOrElse(""))
        val count = datasource.execute_update("DELETE FROM report WHERE id = {rid}", List(NamedParameter("rid", r.id)))
        datasource.execute_update("DROP TABLE " + table)
        count
      case _ => 0
    }
  }

  def report_table(str: String) = s"report_$str"

  def get_jobs(cursor: Int, count: Int, status: Option[String] = None, report: Option[String] = None) = {
    val curNamed = List(NamedParameter("cursor", cursor))
    val limitNamed = List(NamedParameter("limit", count))
    val statusList = status.map(x => List(NamedParameter("sc", "status"), NamedParameter("status", x))).getOrElse(List(NamedParameter("sc", 1), NamedParameter("status", 1)))
    val reportList = report.map(x => List(NamedParameter("rc", "report_id"), NamedParameter("report", x))).getOrElse(List(NamedParameter("rc", 1), NamedParameter("report", 1)))
    datasource.sql_fetchall(SqlValue.getJobs, curNamed ::: limitNamed ::: statusList ::: reportList ).as(Job.parser.*)
  }


  def get_all_jobs() = datasource.sql_fetchall("SELECT * FROM job ORDER BY id ASC").as(Job.parser.*)


  def get_job(job_id: String) = datasource.sql_fetchone("SELECT * FROM job WHERE id = {jid}", List(NamedParameter("jid", job_id)), Some(Job.parser)).asInstanceOf[List[Job]]


  def get_job_status(job_id: String) = datasource.sql_fetchone("SELECT status FROM job WHERE id = {jid}", List(NamedParameter("jid", job_id))).asInstanceOf[List[String]]


  def reset_job_status(job_id: String, reset_to: Option[String]=Some("SUBMITTED")) = {
    val jobsCount = datasource.execute_update(SqlValue.resetJob1,
      List(NamedParameter("stat", reset_to),
        NamedParameter("jid", job_id)))
    datasource.sql_fetchone(SqlValue.resetJob2,
      List(NamedParameter("id", job_id)))
  }


  def reset_jobs_for_report(report_id: String, reset_from: Option[String]=Some("FAILED"), reset_to: Option[String]=Some("SUBMITTED")) = datasource.execute_update(SqlValue.reset_jobs_for_report, List(NamedParameter("to_stat",reset_to), NamedParameter("from_stat",reset_from), NamedParameter("rid",report_id)))



  def reset_stuck_jobs(threshold: Option[Double]=Some(config.STUCK_JOB_THRESHOLD_SEC))= datasource.execute_update("UPDATE job SET status='SUBMITTED' WHERE status='RUNNING' AND TIMESTAMPDIFF(SECOND, executed_at, NOW()) > {threshold}",
    List(NamedParameter("threshold", threshold)))

  val _date_formats = Map(
    "hour" ->  "yyyy-MM-dd HH",
    "day" ->   "yyyy-MM-dd",
    "month" -> "yyyy-MM-01",
    "year" ->  "yyyy-01-01"
  )


  def run_jobs(limit: Option[Int] = None) = limit.map{x => val y = do_job() }

  def get_time_reference() = datasource.sql_fetchone("SELECT frame_reference() AS frame_reference", List.empty, Some(date(1))).asInstanceOf[Option[Date]]


  def set_time_reference(time: Date) = {
    datasource.execute_insert("INSERT INTO time_reference(reference) VALUES ({time}))", List(NamedParameter("time",time)))
    get_time_reference()
  }

  def jobs_summary() = datasource.sql_fetchall("""
SELECT report.id AS id, name, report.status as status,
SUM(CASE WHEN job.status = 'RUNNING' THEN 1 ELSE 0 END) AS running,
SUM(CASE WHEN job.status = 'COMPLETE' THEN 1 ELSE 0 END) AS complete,
SUM(CASE WHEN job.status = 'SUBMITTED' THEN 1 ELSE 0 END) AS submitted,
SUM(CASE WHEN job.status = 'FAILED' THEN 1 ELSE 0 END) AS failed,
COUNT(1) AS total, AVG(TIMEDIFF(completed_at, executed_at)) AS avg_sec
FROM report, job
WHERE report.id = job.report_id
GROUP BY 1, 2
""", List.empty).as(Summary.parser.*)

  def delete_time_reference() = datasource.execute_update("DELETE from time_reference")

  def report_view(id_or_name: String, frame: String, start: String, end: String, dimensions: Seq[(String, String)], metrics: Seq[(String, String)]) = {
    """Select a subset of aggregated data based on the given parameters. If you want a column
    to be both filtered on and displayed, it must be given twice, once with the filtered value
    and once alone (for example, "foo=bar,foo").
    TODO Split dimensions argument into separate dimensions and filters arguments.
    :param id_or_name: report ID or name
    :param frame: aggregation granularity (hour, day, month, year)
    :param start: starting date
    :param end: ending date
    :param dimensions: a comma separated list of dimensions and filters
    :param metrics: a comma separated list of metric column names
    :return: the selected data set
    """

    val report = datasource.sql_fetchone("SELECT id, name  FROM report WHERE id = {rid} OR name = {rid}", List(NamedParameter("rid", id_or_name)), Some(IdAndName.parser)).getOrElse(throw new RuntimeException(id_or_name + " not found")).asInstanceOf[IdAndName]


    val table = report_table(report.name)


      val pulledDimensions = datasource.sql_fetchall("SELECT name FROM dimension WHERE report_id = {rid}",
      List(NamedParameter("rid", report.id))).as(str(1).*)

      val pulledMetrics = datasource.sql_fetchall("SELECT name FROM metric WHERE report_id = {rid}",
      List(NamedParameter("rid", report.id))).as(str(1).*)

    val interval = "cast(DATE_FORMAT(dt, '{0}') AS datetime) AS dt".format(_date_formats.get(frame))
    val date_filter = "dt >= '{0}' AND dt < '{1}'".format(start, end)

    val dimensions_list = Seq(interval)
    val metrics_list = metrics.map(x => s"sum(${x._1}) AS ${x._1}")
    val filter_list = Seq(date_filter)

    // split the specified dimensions into just dimensions (no specified value for column)
    // and filters (using specified value)
    dimensions.map(_._1).++(pulledDimensions).map(x => x.split("=", 1)).map(x => "%s = '%s'".format(x(0), x(1)))

    val dimensions_and_metrics = dimensions_list.++(metrics_list).mkString(",")
    val filters = filter_list.mkString(" AND ")
    val group_by = dimensions_list.zipWithIndex.map{case (_, m) => s"${m + 1}"  }  // produces "1, 2, 3" etc

    val report_sql = "SELECT %s FROM %s WHERE %s GROUP BY %s".format(
      dimensions_and_metrics, table, filters, group_by
    )

    datasource.sql_fetchall(report_sql)
  }
}

case class Summary (
  id: String,
  status: String,
  running: Long,
  complete: Long,
  submitted: Long,
  failed: Long,
  total: Long,
  avg_sec: Double
)
object Summary {
  implicit val parser: RowParser[Summary] = Macro.namedParser[Summary]
}

case class IdAndName(
  id: Double,
  name: String
)
object IdAndName {
  implicit val formats = Json.format[IdAndName]
  val parser: RowParser[IdAndName] = Macro.namedParser[IdAndName]
}

case class JobDetails(
  report: Report,
  requires: List[IdAndName],
  metrics: List[Metrics],
  dimensions: List[Dimensions]
)
object JobDetails {
  implicit val formats = Json.format[JobDetails]
  val parser: RowParser[JobDetails] = Macro.namedParser[JobDetails]
}

case class Dimensions(
  id: Double,
  report_id: Double,
  name: String,
  `type`: String
)
object Dimensions {
  implicit val formats = Json.format[Dimensions]
  val parser: RowParser[Dimensions] = Macro.namedParser[Dimensions]
}

case class Metrics(
  id: Double,
  report_id: Double,
  name: String,
  `type`: String
)
object Metrics {
  implicit val formats = Json.format[Metrics]
  val parser: RowParser[Metrics] = Macro.namedParser[Metrics]
}

object SqlValue {
  val update_submitted_job: String =
    s"""
       |UPDATE job
       |SET    status = 'RUNNING',
       |       executed_at = Now()
       |WHERE  id = {job_id}
       |       AND job.status = 'SUBMITTED'
     """.stripMargin

  val fetch_job_report: String =
    s"""
       |SELECT job.id,
       |       job.aggr_start AS start,
       |       job.aggr_end   AS end,
       |       report.name  AS name,
       |       report.query AS query,
       |       report.part  AS part
       |FROM   job
       |JOIN   report
       |ON     job.report_id = report.id
       |WHERE  job.id = {job_id}
       |LIMIT  1
     """.stripMargin

  val job_candidates: String =
    s"""
       |SELECT job.id
       |FROM   job
       |       LEFT JOIN report
       |              ON job.report_id = report.id
       |WHERE  job.status = 'SUBMITTED'
       |       AND report.status = 'ACTIVE'
       |       AND report_id NOT IN (SELECT r.report_id
       |                             FROM   requires r
       |                                    INNER JOIN job j
       |                                            ON j.report_id = r.depends_on
       |                             WHERE  status IN ( 'SUBMITTED', 'RUNNING', 'FAILED'
       |                                              ))
       |ORDER  BY job.aggr_start DESC
       |LIMIT  10
     """.stripMargin

  val enable_report = "UPDATE report SET status='ACTIVE' WHERE id = {rid}"
  val disable_report = "UPDATE report SET status='DISABLED' WHERE id = {rid}"
  val fail_report: String = "UPDATE report SET status='FAILING' WHERE id = {rid}"

  val to_disable: String =
    s"""
       |SELECT    report.id   AS id,
       |          report.name AS name
       |FROM      job
       |LEFT JOIN report
       |ON        job.report_id = report.id
       |WHERE     job.status='FAILED'
       |AND       report.status='ACTIVE'
       |AND       job.completed_at > Now() - INTERVAL {window}
       |GROUP BY  report.id
       |HAVING    count(*) > {thresh}
     """.stripMargin

  val queue_jobs =
    s"""
       |INSERT INTO job
       |            (
       |                        report_id,
       |                        status,
       |                        aggr_start,
       |                        aggr_end,
       |                        queued_at
       |            )
       |SELECT   id,
       |         'SUBMITTED',
       |         start,
       |end,
       |now()
       |FROM     (
       |                    SELECT     NAME,
       |                               start,
       |         END,
       |         status,
       |         lag,
       |         query,IF(@cur_report = id, @rank = = @rank + 1, @rank = = 1) AS rank,
       |         @cur_report = = id                                     AS id
       |FROM       (
       |                           SELECT          af.id AS id,
       |                                           af.NAME,
       |                                           af.start,
       |                                           af.END,
       |                                           job.status,
       |                                           af.lag,
       |                                           af.query
       |                           FROM            all_frames af
       |                           LEFT OUTER JOIN job
       |                           ON              af.id = job.report_id
       |                           AND             af.start = job.aggr_start
       |                           ORDER BY        af.id,
       |                                           af.start DESC ) sub
       |INNER JOIN
       |           (
       |                  SELECT @rank = =       0,
       |                         @cur_report = = 0,
       |                         @inc = =        0) r ) sub2
       |WHERE    status IS NULL
       |ORDER BY start ASC,
       |         id ASC;
    """.stripMargin


  val jobs_summary =
    s"""
       |SELECT report.id                                AS id,
       |       name,
       |       report.status                            AS status,
       |       Sum(CASE
       |             WHEN job.status = 'RUNNING' THEN 1
       |             ELSE 0
       |           end)                                 AS running,
       |       Sum(CASE
       |             WHEN job.status = 'COMPLETE' THEN 1
       |             ELSE 0
       |           end)                                 AS complete,
       |       Sum(CASE
       |             WHEN job.status = 'SUBMITTED' THEN 1
       |             ELSE 0
       |           end)                                 AS submitted,
       |       Sum(CASE
       |             WHEN job.status = 'FAILED' THEN 1
       |             ELSE 0
       |           end)                                 AS failed,
       |       Count(1)                                 AS total,
       |       Avg(Timediff(completed_at, executed_at)) AS avg_sec
       |FROM   report,
       |       job
       |WHERE  report.id = job.report_id
       |GROUP  BY 1,
       |          2
     """.stripMargin
  val get_time_reference =
    """
      |SELECT frame_reference() AS frame_reference
    """.stripMargin

  val set_time_reference = "INSERT INTO time_reference(reference) VALUES ({time})"

  val delete_time_reference = "DELETE from time_reference"

  val report_sql = "SELECT {dimensions_and_metrics} FROM {table} WHERE {filters} GROUP BY {group_by}"

  val report = "SELECT id, name  FROM report WHERE id = {rid} OR name = {rid}"

  val updateJob = "UPDATE job SET status='SUBMITTED' WHERE status='RUNNING' "
  "AND TIMESTAMPDIFF(SECOND, executed_at, NOW()) > {threshold}"

  val resetJob1 = "UPDATE job SET status={status} WHERE id = {id}"
  val resetJob2 = "SELECT * FROM job WHERE id = {id}"

  val getJobs =
    """
      |SELECT * FROM job WHERE id > {cursor} "
      |  "AND {sc}={status} "
      |  "AND {rc}={report} "
      |  "ORDER BY id ASC "
      |  "LIMIT {limit}
    """.stripMargin

  val reset_jobs_for_report =
    s"""
       |UPDATE job SET status={to_stat} WHERE report_id = {rid} AND status={from_stat}
     """.stripMargin

  val get_job_status =
    s"""
       |SELECT status FROM job WHERE id = {id}
    """.stripMargin


  val get_all_jobs = "SELECT * FROM job ORDER BY id ASC"

  val get_job = "SELECT * FROM job WHERE id = {id}"

  val job_id = "SELECT status FROM job WHERE id = {jid}"

  val delete_report = "DROP TABLE #{table}"

  val get_report = "SELECT * FROM report WHERE id = {rid} OR name = {rid}"

  val get_required = "SELECT id, name FROM requires req INNER JOIN report rep ON req.depends_on = rep.id WHERE req.report_id = {rid}"

  val requires =
    s"""
       |SELECT id,
       |       NAME
       |FROM   requires req
       |       INNER JOIN report rep
       |               ON req.depends_on = rep.id
       |WHERE  req.report_id = {report_id}
     """.stripMargin

  val metrics = "SELECT * from metric WHERE report_id = {report_id}"

  val dimensions = "SELECT * from dimension WHERE report_id = {report_id}"

  val get_report_cursor = "SELECT * FROM report WHERE id > {cursor} ORDER BY id ASC LIMIT {limit}"

  val all_reports = "SELECT * FROM report ORDER by id ASC"

  val complete_job =
    s"""
       |UPDATE job
       |SET    status = 'COMPLETE',
       |       attempts = attempts + 1,
       |       completed_at = Now(),
       |       records = {records}
       |WHERE  id = {job_id}
     """.stripMargin

  val fail_job =
    s"""
       |UPDATE job
       |SET    status = 'FAILED',
       |       attempts = attempts + 1,
       |       completed_at = Now(),
       |       fail = {fail}
       |WHERE  id = {job_id}
     """.stripMargin

  val fecth_one_job = "SELECT * FROM job JOIN report ON job.report_id = report.id WHERE job.id = {job_id} LIMIT 1"

  val all_dependent_reports = "SELECT * FROM report WHERE id IN (SELECT report_id FROM requires)"

  val update_active_report = "UPDATE report SET status='ACTIVE' WHERE status='RETIRED' AND name IN ({name})"
  val update_retired_report = "UPDATE report SET status='RETIRED' WHERE status='ACTIVE' AND name NOT IN ({name})"

  val insert_report =
    s"""
       |INSERT INTO report
       |            (NAME,
       |             frame,
       |             bootstrap,
       |             lag,
       |             query,
       |             part)
       |VALUES      ({name},
       |             {frame},
       |             {bootstrap},
       |             {lag},
       |             {query},
       |             {partition})
     """.stripMargin

  val last_id = "SELECT LAST_INSERT_ID() AS id"

  val insert_dimension = """REPLACE INTO dimension(report_id, name, type) VALUES ({report_id}, {name}, {type})"""
  val insert_metric = """REPLACE INTO metric(report_id, name, type) VALUES ({report_id}, {name}, {type})"""

  val requirement =
    s"""
       |REPLACE INTO requires(report_id, depends_on) SELECT {rid}, id FROM report WHERE
       |name = {req}
     """.stripMargin

  val create_table_schema =
    s"""
       |CREATE TABLE {name}
       |             (
       |                    job_id      BIGINT UNSIGNED,
       |                    {partition} datetime,
       |                    {dimensions},
       |                    {metrics},
       |                    FOREIGN KEY ref_job_{name} (job_id) REFERENCES job(id) ON DELETE SET NULL,
       |                    PRIMARY KEY({PRIMARY})
       |             )
       |             engine=innodb DEFAULT charset=utf8
     """.stripMargin

  val post_create_fetch = "SELECT * FROM report WHERE id = {rid}"

  val replace_table_values =
    s"""
       |REPLACE INTO {table} VALUES ({job_id}, {spec})
     """.stripMargin

}
