package repositories.mongo

import javax.inject.Inject
import models.{Address}
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


class AddressRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import Address._

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

  def getAddress(id: BSONObjectID): Future[Option[Address]] = {
    val query = BSONDocument("_id" -> id)
    AddressCollection.flatMap(_.find(query).one[Address])
  }
}