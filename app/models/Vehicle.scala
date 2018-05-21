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


case class Vehicle(_id: Option[BSONObjectID], vehicleInfo: VehicleInfo)

object Vehicle {
  import play.api.libs.json._

  implicit val formats: OFormat[Vehicle] = Json.format[Vehicle]
}

case class VehicleInfo(
                            year: String,
                            make: String,
                            model: String,
                            vehicleStyle: String,
                            vehicleColor: String,
                            vehicleTagNumber: String,
                            vehicleTagYear: String,
                            vehicleTagState: String,
                            vinNumber: String,
                            notes: String
                          )

object VehicleInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[VehicleInfo] = Json.format[VehicleInfo]
}

class VehicleRepository @Inject()(implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) {

  import Vehicle._
  import VehicleInfo._
  import reactivemongo.util.LazyLogger._
  val logger = LazyLogger.apply(this.getClass.getName)

  def vehicleCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("Vehicle"))

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
}