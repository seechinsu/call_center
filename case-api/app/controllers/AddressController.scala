package controllers

import javax.inject.Inject
import io.swagger.annotations._
import models.{Address}
import repositories.mongo.{AddressRepository}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import reactivemongo.bson.BSONObjectID

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Api(value = "/address")
class AddressController @Inject()(cc: ControllerComponents, addressRepo: AddressRepository) extends AbstractController(cc) {

  @ApiOperation(
    value = "Find all Addresses",
    response = classOf[Address],
    responseContainer = "List"
  )
  def getAllAddresses = Action.async {
    addressRepo.getAllTs.map { address =>
      Ok(Json.toJson(address))
    }
  }

  @ApiOperation(
    value = "Add a new address to the list",
    response = classOf[Void],
    code = 201
  )
  @ApiResponses(
    Array(
      new ApiResponse(code = 400, message = "Invalid address format")
    )
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        value = "The address to add, in Json Format",
        required = true,
        dataType = "models.Address",
        paramType = "body"
      )
    )
  )
  def createAddress() = Action.async(parse.json) { req =>
    req.body.validate[Address].map { addressData =>
      addressRepo.addT(addressData).map { _ =>
        Created
      }
    }.getOrElse(Future.successful(BadRequest("Invalid address format")))
  }

  @ApiOperation(
    value = "Delete a address",
    response = classOf[Address]
  )
  def deleteAddress(@ApiParam(value = "The id of the address to delete") addressId: BSONObjectID) = Action.async{ req =>
    addressRepo.deleteT(addressId).map {
      case Some(address) => Ok(Json.toJson(address))
      case None => NotFound
    }
  }

  @ApiOperation(
    value = "Find an address by id",
    response = classOf[Address]
  )
  def getAddress(@ApiParam(value = "The id of the address to retrieve") addressId: BSONObjectID) = Action.async{ req =>
    addressRepo.getT(addressId).map { maybeAddress =>
      maybeAddress.map { address =>
        Ok(Json.toJson(address))
      }.getOrElse(NotFound)
    }
  }
}


