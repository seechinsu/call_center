package repositories.mongo

import javax.inject.Inject
import models.{Vehicle}
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


class VehicleRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import Vehicle._

  val logger = LazyLogger.apply(this.getClass.getName)

  val vehicleCollection: Future[JSONCollection] = reactiveMongoApi.database.map{
    case x => x.collection("Vehicle")
  }

  def getAll: Future[Seq[Vehicle]] = {
    val query = Json.obj()
    vehicleCollection.flatMap{
      case collection =>
        val queryCollection = collection.find(query)
        val queryCursor = queryCollection.cursor[Vehicle](ReadPreference.primary)
        queryCursor.collect[Seq](5000, Cursor.FailOnError[Seq[Vehicle]]({case x => logger.error(x.toString())}))
    }
  }

  def addVehicle(vehicle: Vehicle): Future[WriteResult] = {
    vehicleCollection.flatMap(_.insert(vehicle))
  }

  def deleteVehicle(id: BSONObjectID): Future[Option[Vehicle]] = {
    val selector = BSONDocument("_id" -> id)
    vehicleCollection.flatMap(_.findAndRemove(selector).map(_.result[Vehicle]))
  }

  def getVehicle(id: BSONObjectID): Future[Option[Vehicle]] = {
    val query = BSONDocument("_id" -> id)
    vehicleCollection.flatMap(_.find(query).one[Vehicle])
  }
}