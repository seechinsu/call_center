package models

import javax.inject.Inject

import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.util.LazyLogger
import reactivemongo.util.LazyLogger.LazyLogger

import java.time.Instant
import java.time.LocalDate
import scala.concurrent.{ExecutionContext, Future}


case class Case(_id: Option[BSONObjectID], primaryCaseInfo: PrimaryCaseInfo)

object Case {
  import play.api.libs.json._

  implicit val formats: OFormat[Case] = Json.format[Case]
}

object PrimaryCaseInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[PrimaryCaseInfo] = Json.format[PrimaryCaseInfo]
}

case class PrimaryCaseInfo(
  caseType: String,
  requestType: String,
  operator: String,
  aniNumber: String,
  outOfRange: Boolean,
  source: String,
  timeStamp: Long = Instant.now().toEpochMilli,
  referredBy: String,
  relatedCases: Int,
  events: String,
  amberAlertFlag: Boolean,
  sourceOrganization: String,
  referredToAmeco: Boolean,
  amecoNpo: String
)


class CaseRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import Case._
  import PrimaryCaseInfo._
  import reactivemongo.util.LazyLogger._
  val logger = LazyLogger.apply(this.getClass.getName)

  val caseCollection: Future[JSONCollection] = reactiveMongoApi.database.map{
    case x => x.collection("Case")
  }

  def getAll: Future[Seq[Case]] = {
    val query = Json.obj()
    caseCollection.flatMap{
      case collection =>
       val queryCollection = collection.find(query)
       val queryCursor = queryCollection.cursor[Case](ReadPreference.primary)
       queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[Case]]({case x => logger.error(x.toString())}))
    }
  }

  def add(`case`: Case): Future[WriteResult] = {
    caseCollection.flatMap(_.insert(`case`))
  }
}
