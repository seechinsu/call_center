package controllers

import io.swagger.annotations._
import javax.inject.Inject
import models.ContactInfo
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import reactivemongo.bson.BSONObjectID
import repositories.mongo.ContactInfoRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Api(value = "/contact")
class ContactInfoController @Inject()(cc: ControllerComponents, contactInfoRepo: ContactInfoRepository) extends AbstractController(cc) {

  @ApiOperation(
    value = "Find all Contact Info",
    response = classOf[ContactInfo],
    responseContainer = "List"
  )
  def getAllContactInfo = Action.async {
    contactInfoRepo.getAllTs.map { contact =>
      Ok(Json.toJson(contact))
    }
  }

  @ApiOperation(
    value = "Add a new contact info to the list",
    response = classOf[Void],
    code = 201
  )
  @ApiResponses(
    Array(
      new ApiResponse(code = 400, message = "Invalid contact info format")
    )
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        value = "The contact info to add, in Json Format",
        required = true,
        dataType = "models.ContactInfo",
        paramType = "body"
      )
    )
  )
  def createContactInfo() = Action.async(parse.json) { req =>
    req.body.validate[ContactInfo].map { contactData =>
      contactInfoRepo.addT(contactData).map { _ =>
        Created
      }
    }.getOrElse(Future.successful(BadRequest("Invalid contact info format")))
  }

  @ApiOperation(
    value = "Delete a contact info",
    response = classOf[ContactInfo]
  )
  def deleteContactInfo(@ApiParam(value = "The id of the contact info to delete") contactId: BSONObjectID) = Action.async{ req =>
    contactInfoRepo.deleteT(contactId).map {
      case Some(contact) => Ok(Json.toJson(contact))
      case None => NotFound
    }
  }

  @ApiOperation(
    value = "Find a contact info by id",
    response = classOf[ContactInfo]
  )
  def getContactInfo(@ApiParam(value = "The id of the contact info to retrieve") contactId: BSONObjectID) = Action.async{ req =>
    contactInfoRepo.getT(contactId).map { contact =>
      Ok(Json.toJson(contact))
    }
  }
}