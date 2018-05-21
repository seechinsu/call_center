package controllers

import javax.inject.Inject
import io.swagger.annotations._
import models.{Vehicle, VehicleRepository}

import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import reactivemongo.bson.BSONObjectID

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Api(value = "/vehicle")
class VehicleController @Inject()(cc: ControllerComponents, vehicleRepo: VehicleRepository) extends AbstractController(cc) {

  @ApiOperation(
    value = "Find all Vehicles",
    response = classOf[Vehicle],
    responseContainer = "List"
  )
  def getAllVehicles = Action.async {
    vehicleRepo.getAll.map { vehicle =>
      Ok(Json.toJson(vehicle))
    }
  }

  @ApiOperation(
    value = "Add a new vehicle to the list",
    response = classOf[Void],
    code = 201
  )
  @ApiResponses(
    Array(
      new ApiResponse(code = 400, message = "Invalid vehicle format")
    )
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        value = "The vehicle to add, in Json Format",
        required = true,
        dataType = "models.Vehicle",
        paramType = "body"
      )
    )
  )
  def createVehicle() = Action.async(parse.json) { req =>
    req.body.validate[Vehicle].map { vehicleData =>
      vehicleRepo.addVehicle(vehicleData).map { _ =>
        Created
      }
    }.getOrElse(Future.successful(BadRequest("Invalid Vehicle format")))
  }

  @ApiOperation(
    value = "Delete a law enforcement agent",
    response = classOf[Vehicle]
  )
  def deleteVehicle(@ApiParam(value = "The id of the vehicle to delete") vehicleId: BSONObjectID) = Action.async{ req =>
    vehicleRepo.deleteVehicle(vehicleId).map {
      case Some(vehicle) => Ok(Json.toJson(vehicle))
      case None => NotFound
    }
  }
}


