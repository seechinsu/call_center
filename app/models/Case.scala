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
import java.time.Instant
import scala.concurrent.{ExecutionContext, Future}


case class Case(_id: Option[BSONObjectID], primaryCaseInfo: PrimaryCaseInfo)

object Case {
  import play.api.libs.json._

  implicit val formats: OFormat[Case] = Json.format[Case]
}

case class PrimaryCaseInfo(
                            _id: Option[BSONObjectID],
                            caseType: String,
                            requestType: String,
                            operator: String,
                            dateOfCall: Long = Instant.now().toEpochMilli,
                            aniNumber: String,
                            outOfRange: Boolean,
                            source: String,
                            referredBy: String,
                            relatedCases: Int,
                            events: String,
                            amberAlertFlag: Boolean,
                            sourceOrganization: String,
                            referredToAmeco: Boolean,
                            amecoNpo: String
                            //lawEnforcementInfo: Seq[LawEnforcement],
                            //vehicle: Seq[Vehicle],
                            //narrative: Seq[Narrative],
                            //people: Seq[Person]
)

object PrimaryCaseInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[PrimaryCaseInfo] = Json.format[PrimaryCaseInfo]
}

class CaseRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import Case._
  import PrimaryCaseInfo._
  import reactivemongo.util.LazyLogger._
  val logger = LazyLogger.apply(this.getClass.getName)

  val caseCollection: Future[JSONCollection] = reactiveMongoApi.database.map{
    case x => x.collection("Case")
  }

  def getAllCases: Future[Seq[Case]] = {
    val query = Json.obj()
    caseCollection.flatMap{
      case collection =>
       val queryCollection = collection.find(query)
       val queryCursor = queryCollection.cursor[Case](ReadPreference.primary)
       queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[Case]]({case x => logger.error(x.toString())}))
    }
  }

  def addCase(`case`: Case): Future[WriteResult] = {
    caseCollection.flatMap(_.insert(`case`))
  }

  def deleteCase(id: BSONObjectID): Future[Option[Case]] = {
    val selector = BSONDocument("_id" -> id)
    caseCollection.flatMap(_.findAndRemove(selector).map(_.result[Case]))
  }

  def getCase(id: BSONObjectID): Future[Seq[Case]] = {
    val selector = BSONDocument("_id" -> id)
    caseCollection.flatMap{
      case collection =>
        val queryCollection = collection.find(selector)
        val queryCursor = queryCollection.cursor[Case](ReadPreference.primary)
        queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[Case]]({case x => logger.error(x.toString())}))
    }
  }

}
