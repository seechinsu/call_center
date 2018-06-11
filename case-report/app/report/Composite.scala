package report

import anorm.{Macro, NamedParameter, RowParser}

import com.typesafe.scalalogging.LazyLogging
import play.api.Configuration
import play.api.libs.json.{JsString, Json}

import java.nio.file.Path
import javax.inject.Inject


case class CompositeReport(name: String, query: String)

object CompositeReport {
  implicit val parser: RowParser[CompositeReport] = Macro.namedParser[CompositeReport]
  implicit val writes = Json.format[CompositeReport]
}

class CompositeService @Inject()(
  datasource: Datasource,
  config: AppConfig,
  yamlLoader: YamlLoader
) extends LazyLogging {

  val logging = logger
  val app_path = config.APP_PATH
  val report_path = config.PATH_REPORTS
  val evolutions_path = config.PATH_EVOLUTIONS
  val composite_path = config.PATH_COMPOSITES


  def autoload_composites(path: String) = {
    yamlLoader.load_yaml_from_path[CompositeReport](path).foreach { case report: CompositeReport => create_composite(report.name, report.query) }
  }
    


  def delete_missing_composites(path: String) = {
    """Remove composites based on its absence from the composite folder."""
    val local_report_names = yamlLoader.load_yaml_from_path(path).map{case x: CompositeReport => s"""|'${x.name}'""".stripMargin}.mkString(",")
    datasource.sql_count("delete from composite where name NOT IN ({local_report_names})")
  }

  /**assert '{framed}' in query or \
    ('{start_datetime}' in query and '{end_datetime}' in query), \
  "query must include {framed} or {start_datetime} and {end_datetime} in a WHERE clause (see README)"
  assert '{filtered}' in query, "composite report queries must include {filtered} in the WHERE clause"
    **/
  def create_composite(name: String, query: String) = {
    datasource.execute_insert("INSERT INTO composite(name, query) VALUES ({name}, {query}) ON DUPLICATE KEY UPDATE query={query}",
      List(NamedParameter("name", name), NamedParameter("query", query)))
    datasource.sql_fetchone("SELECT * from composite WHERE name={name}", List(NamedParameter("name", name)))
  }


  def create_composite_from_yaml(yml: CompositeReport) = create_composite(yml.name, yml.query)


  def composite_view(name: String, start: String, end: String, filters: String, offset: String, limit: String) = {
    """
    :param name: composite report name
    :param start: starting datetime
    :param end: ending datetime
    :param filters: list of key=value pairs
    :param offset: paging offset
    :param limit: returned row limit
    :return:
    """
    val report = datasource.sql_fetchone[CompositeReport](
      "SELECT name, query FROM composite WHERE name = {name}",
      List(NamedParameter("name", name)), Some(CompositeReport.parser)
    ).asInstanceOf[Option[CompositeReport]]

    val framed = s"dt >= '${start}' AND dt < '${end}'"
    val start_datetime = s"cast('${start}' as datetime)"
    val end_datetime = s"cast('${end}' as datetime)"
    val filter_list = filters.split(",").map { case column => {
      s"${column.split("=", 1).head} = '${
        column.split(
          "=",
          1
        ).tail.head
      }'"
    }
    }


    datasource.sql_fetchall_json(
      report.get.query + " AND LIMIT {limit} OFFSET {offset} ",
      List(
        NamedParameter("limit", limit),
        NamedParameter("offset", offset),
        NamedParameter("framed", framed),
        NamedParameter("start_datetime", start_datetime),
        NamedParameter("end_datetime", end_datetime),
        NamedParameter("filtered", filter_list)
      )
    )


  }

  def get_composite(name: String) = datasource.sql_fetchone("SELECT * FROM composite WHERE name={name}", List(NamedParameter("limit", name)))


  def get_all_composites() = datasource.sql_fetchall("SELECT * from composite")


  def delete_composite(name: String) = datasource.sql_count("DELETE FROM composite WHERE name={name}", List(NamedParameter("limit", name)))


  def delete_all_composites() = datasource.execute_update("DELETE FROM composite")

}

import javax.inject.{Inject, Provider}

import com.typesafe.config.Config
import play.api.Configuration

// inject this class for access to global configuration
case class AppConfig(
  APP_PATH: String,
  PATH_REPORTS: String,
  PATH_EVOLUTIONS: String,
  PATH_COMPOSITES: String,
  REPORT_DISABLE_THRESHOLD: String,
  REPORT_DISABLE_WINDOW: String,
  STUCK_JOB_THRESHOLD_SEC: Double
)

object AppConfig {

  def apply(config: Config): AppConfig = {
    val appConfig = AppConfig(
      // load from conf here
      config.getString("app.path"),
      config.getString("path.reports"),
      config.getString("path.evolutions"),
      config.getString("path.composites"),
      config.getString("report.disable.threshold"),
      config.getString("report.disable.window"),
      config.getDouble("stuck.job.threshold.sec")
    )
    appConfig
  }

}

class AppConfigProvider @Inject()(config: Configuration) extends Provider[AppConfig] {
  override def get(): AppConfig = AppConfig(config.underlying)
}
