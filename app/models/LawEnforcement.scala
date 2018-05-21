package models

import java.time.{Instant, LocalDate}

import javax.inject.Inject
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.util.LazyLogger

import scala.concurrent.{ExecutionContext, Future}


case class LawEnforcement(_id: Option[BSONObjectID], lawEnforcementInfo: LawEnforcementInfo)

object LawEnforcement {

  import play.api.libs.json._

  implicit val formats: OFormat[LawEnforcement] = Json.format[LawEnforcement]
}

case class LawEnforcementInfo(
                       dateReported: LocalDate,
                       assignedOfficer: String,
                       respondingOfficer: String,
                       department: String,
                       unit: String,
                       signedMediaWaiver: Boolean,
                       receiveLeads: Boolean,
                       //mobilePhone: Seq[String],
                       //generalPhone: Seq[String],
                       //directPhone: Seq[String],
                       //posterPhone: Seq[String],
                       //faxPhone: Seq[String],
                       //agencyEmail: Seq[String],
                       //officerEmail: Seq[String],
                       ocaNumber: String,
                       oriNumber: String,
                       childIssuingAgency: String,
                       streetAddress: String,
                       city: String,
                       zip: String,
                       county: String,
                       state: String,
                       country: String,
                       notes: String
                     )

object LawEnforcementInfo {

  import play.api.libs.json._

  implicit val formats: OFormat[LawEnforcementInfo] = Json.format[LawEnforcementInfo]
}

class LawEnforcementRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import LawEnforcement._
  val logger = LazyLogger.apply(this.getClass.getName)

  def lawEnforcementCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("LawEnforcement"))

  def getAll: Future[Seq[LawEnforcement]] = {
    val query = Json.obj()
    lawEnforcementCollection.flatMap{
      case collection =>
        val queryCollection = collection.find(query)
        val queryCursor = queryCollection.cursor[LawEnforcement](ReadPreference.primary)
        queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[LawEnforcement]]({case x => logger.error(x.toString())}))
    }
  }

  def addLawEnforcement(lawEnforcement: LawEnforcement): Future[WriteResult] = {
    lawEnforcementCollection.flatMap(_.insert(lawEnforcement))
  }

  def deleteLawEnforcement(id: BSONObjectID): Future[Option[LawEnforcement]] = {
    val selector = BSONDocument("_id" -> id)
    lawEnforcementCollection.flatMap(_.findAndRemove(selector).map(_.result[LawEnforcement]))
  }
}