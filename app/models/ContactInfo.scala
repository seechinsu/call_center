package models

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


case class ContactInfo(_id: Option[BSONObjectID], contactInfoDetail: ContactInfoDetail)

object ContactInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[ContactInfo] = Json.format[ContactInfo]
}

case class ContactInfoDetail(
                            _id: Option[BSONObjectID],
                            //mobilePhones: Seq[String],
                            //homePhones: Seq[String],
                            //workPhones: Seq[String],
                            //emails: Seq[String]
)

object ContactInfoDetail {
  import play.api.libs.json._

  implicit val formats: OFormat[ContactInfoDetail] = Json.format[ContactInfoDetail]
}

class ContactInfoRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import ContactInfo._
  val logger = LazyLogger.apply(this.getClass.getName)

  val contactInfoCollection: Future[JSONCollection] = reactiveMongoApi.database.map{
    case x => x.collection("ContactInfo")
  }

  def getAllContactInfo: Future[Seq[ContactInfo]] = {
    val query = Json.obj()
    contactInfoCollection.flatMap{
      case collection =>
       val queryCollection = collection.find(query)
       val queryCursor = queryCollection.cursor[ContactInfo](ReadPreference.primary)
       queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[ContactInfo]]({case x => logger.error(x.toString())}))
    }
  }

  def addContactInfo(contactInfo: ContactInfo): Future[WriteResult] = {
    contactInfoCollection.flatMap(_.insert(contactInfo))
  }

  def deleteContactInfo(id: BSONObjectID): Future[Option[ContactInfo]] = {
    val selector = BSONDocument("_id" -> id)
    contactInfoCollection.flatMap(_.findAndRemove(selector).map(_.result[ContactInfo]))
  }

  def getContactInfo(id: BSONObjectID): Future[Seq[ContactInfo]] = {
    val selector = BSONDocument("_id" -> id)
    contactInfoCollection.flatMap{
      case collection =>
        val queryCollection = collection.find(selector)
        val queryCursor = queryCollection.cursor[ContactInfo](ReadPreference.primary)
        queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[ContactInfo]]({case x => logger.error(x.toString())}))
    }
  }

}
