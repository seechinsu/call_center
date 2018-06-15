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


case class Vehicle(_id: Option[BSONObjectID] = Some(BSONObjectID.generate()), vehicleInfo: VehicleInfo)

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
                        notes: Seq[Narrative]
                      )

object VehicleInfo {
  import play.api.libs.json._

  implicit val formats: OFormat[VehicleInfo] = Json.format[VehicleInfo]
}
