package report

import anorm._

import com.typesafe.scalalogging.LazyLogging
import play.api.Configuration
import play.api.db._
import play.api.libs.json.{JsValue, Json, Writes}

import java.sql.ResultSet
import javax.inject.Inject
import scala.util.{Failure, Try}

/**
  * Created by tashdidkhan on 27/05/2018.
  */
class Warehouse @Inject()(config: Configuration, @NamedDatabase("presto") db: Database) extends LazyLogging {

  val JOB_FETCH_SIZE = "job.fetch.size"
  val fetchSize = config.get[Int](JOB_FETCH_SIZE)


  def connection = db

  def catalogHealthy(name: String) = Try {
    db.withConnection(implicit connection => SqlQueryResult(resource.managed(connection.createStatement().executeQuery(s"SELECT * FROM $name WHERE 0 = 1")))).as(
      SqlParser.bool(1).*
    ).headOption
  } match {
    case Success(rs) => true
    case Failure(ex) => false
  }

  def healthy(): Boolean = Try {
    db.withConnection(
      implicit connection => SqlQueryResult(
        resource.managed(
          connection.createStatement().executeQuery(
            "Select 1"
          )
        )
      )
    ).as(SqlParser.bool(1).*).headOption
  } match {
    case Success(rs) => true
    case Failure(ex) => false
  }

  def sql_fetchall[T](query: String, args: List[NamedParameter] = List.empty): Iterator[ResultSet] = db.withTransaction
  { implicit conn =>
    val namedParser: RowParser[T] = Macro.namedParser[T]
    resource.managed(conn.createStatement().executeQuery(query)).map {
      rs =>
        rs.setFetchSize(fetchSize)
        Iterator.continually((rs.next(), rs)).takeWhile(_._1).map(r => r._2)
    }.acquireFor(x => x).either match {
      case Left(t) => throw t.head
      case Right(x) => x
    }
  }


  def sql_fetchone(query: String, args: List[NamedParameter] = List.empty) = sql_fetchall(query, args).toSeq.headOption


  def sql_count(query: String, args: List[NamedParameter] = List.empty) = sql_fetchall(query, args).toSeq.size


  def fetch_pages(query: String, args: List[NamedParameter] = List.empty, size: Int = fetchSize)(processPage: Iterator[Page] => Iterator[Int]):Iterator[Int] = {
    db.withConnection { implicit conn =>
      val rs = conn.createStatement().executeQuery(query)
      rs.setFetchSize(fetchSize)
      processPage(Iterator.continually((rs.next(), rs)).takeWhile(x => x._1).map(r => r._2).sliding(fetchSize).map(Page))
    }
  }

  def sql_fetchall_parser[T](
    query: String,
    args: List[NamedParameter] = List.empty,
    rowParser: RowParser[T]
  ): Either[List[Throwable], Seq[Try[T]]] = db.withTransaction { implicit conn =>
    SQL(query).on(args: _*).fold(Seq.empty[Try[T]], ColumnAliaser.empty) {
      case (result, element) =>
        val e = element.as(rowParser)
        result.++(Seq(e)).flatten
    }
  }
}

case class Page(resultSets: Seq[ResultSet])
