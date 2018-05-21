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

case class Address(_id: Option[BSONObjectID], addressInfo: AddressInfo)

object Address {
  import play.api.libs.json._

  implicit val formats: OFormat[Address] = Json.format[Address]
}

case class AddressInfo(
                            addressDescription: String,
                            addressType: String,
                            streetAddress: String,
                            city: String,
                            zip: String,
                            county: String,
                            state: String,
                            country: String
)

object AddressInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[AddressInfo] = Json.format[AddressInfo]
}

class AddressRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import Address._
  import AddressInfo._
  val logger = LazyLogger.apply(this.getClass.getName)

  val AddressCollection: Future[JSONCollection] = reactiveMongoApi.database.map{
    case x => x.collection("Address")
  }

  def getAllAddresses: Future[Seq[Address]] = {
    val query = Json.obj()
    AddressCollection.flatMap{
      case collection =>
       val queryCollection = collection.find(query)
       val queryCursor = queryCollection.cursor[Address](ReadPreference.primary)
       queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[Address]]({case x => logger.error(x.toString())}))
    }
  }

  def addAddress(address: Address): Future[WriteResult] = {
    AddressCollection.flatMap(_.insert(address))
  }

  def deleteAddress(id: BSONObjectID): Future[Option[Address]] = {
    val selector = BSONDocument("_id" -> id)
    AddressCollection.flatMap(_.findAndRemove(selector).map(_.result[Address]))
  }

  def getAddress(id: BSONObjectID): Future[Seq[Address]] = {
    val selector = BSONDocument("_id" -> id)
    AddressCollection.flatMap{
      case collection =>
        val queryCollection = collection.find(selector)
        val queryCursor = queryCollection.cursor[Address](ReadPreference.primary)
        queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[Address]]({case x => logger.error(x.toString())}))
    }
  }
}
