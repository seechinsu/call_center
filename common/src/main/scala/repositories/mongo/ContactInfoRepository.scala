package repositories.mongo

import javax.inject.Inject
import models.ContactInfo
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.util.LazyLogger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

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