package report

import anorm._
import anorm.SqlParser.str
import resource.managed

import com.typesafe.scalalogging.LazyLogging
import play.api.Configuration
import play.api.db._
import play.api.libs.json.{JsString, Json}

import java.sql.ResultSet
import javax.inject.Inject
import scala.util.{Failure, Try, Success => TrySuccess}

/**
  * Created by tashdidkhan on 27/05/2018.
  */
class Datasource @Inject()(config: Configuration, @NamedDatabase("mysql") db: Database) extends LazyLogging {
  def sql_fetchall_json(str: String, parameters: List[NamedParameter]) = sql_fetchall(str, parameters).resultSet.acquireFor{rs => val counter = rs.getMetaData.getColumnCount
    Range(0, counter).map(i => rs.getMetaData().getColumnLabel(i + 1).toLowerCase() -> Option(rs.getObject(i + 1)).map(_.toString).getOrElse("")).foldLeft(Json.obj()){case (obj, kv) => obj.+(kv._1, JsString(kv._2))}
  }


  val JOB_FETCH_SIZE = "job.fetch.size"
  val fetchSize = config.get[Int](JOB_FETCH_SIZE)

  def sql_fetchall(query: String, args: List[NamedParameter] = List.empty) = db.withTransaction { implicit conn =>
    SQL(query).on(args: _*).executeQuery()
  }


  def sql_fetchone[A](query: String, args: List[NamedParameter] = List.empty, parserOpt: Option[RowParser[A]] = None) = sql_fetchall(query, args).as(parserOpt.getOrElse(str(1)).*).headOption


  def sql_count[A](query: String, args: List[NamedParameter] = List.empty, parserOpt: Option[RowParser[A]] = None) = sql_fetchall(query, args).as(parserOpt.getOrElse(str(1)).*).size


  def  execute_insert(query: String, args: List[NamedParameter] = List.empty) = db.withTransaction { implicit conn =>
    SQL(query).on(args: _*).executeInsert(str(1).singleOpt)
  }

  def execute_update(query: String, args: List[NamedParameter] = List.empty) = db.withTransaction { implicit conn =>
    SQL(query).on(args: _*).executeUpdate()
  }

  def execute_bulk_insert(query: String, argList: Seq[Seq[NamedParameter]]) = db.withTransaction { implicit conn =>
    BatchSql(query, argList.head, argList.tail : _*).execute()
  }

  def healthy():Boolean =  Try {
    sql_fetchall("SELECT 1", List.empty)
  } match {
    case Success(rs) => true
    case Failure(ex) => false
  }


}
