package controllers

import client.KafkaProducerClient
import io.swagger.annotations._
import javax.inject.Inject
import models.Lead
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import reactivemongo.bson.BSONObjectID
import repositories.mongo.{LeadRepository}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Api(value = "/leads")
class LeadController @Inject()(cc: ControllerComponents, leadRepo: LeadRepository, producer: KafkaProducerClient) extends AbstractController(cc) {

  @ApiOperation(
    value = "Find all Lead",
    response = classOf[Lead],
    responseContainer = "List"
  )
  def getAllLeads = Action.async {
    leadRepo.getAllTs.map { cases =>
      Ok(Json.toJson(cases))
    }
  }

  @ApiOperation(
    value = "Add a new lead to the list",
    response = classOf[Void],
    code = 201
  )
  @ApiResponses(
    Array(
      new ApiResponse(code = 400, message = "Invalid lead format")
    )
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        value = "The lead to add, in Json Format",
        required = true,
        dataType = "models.Lead",
        paramType = "body"
      )
    )
  )
  def createLead() = Action.async(parse.json) { req =>
    req.body.validate[Lead].map { leadData =>
//      for { _ <- caseRepo.addLead(leadData)
//            _ <- producer.publish(EventWrapper.apply("lead-added", serialize.serialize(leadData)))
//      } yield Created
      leadRepo.addT(leadData).map { _ =>
        Created
      }
    }.getOrElse(Future.successful(BadRequest("Invalid Lead format")))
  }

  @ApiOperation(
    value = "Delete a lead",
    response = classOf[Lead]
  )
  def deleteLead(@ApiParam(value = "The id of the lead to delete") leadId: BSONObjectID) = Action.async{ req =>
    leadRepo.deleteT(leadId).map {
      case Some(leadid) => Ok(Json.toJson(leadid))
      case None => NotFound
    }
  }

  @ApiOperation(
    value = "Find a lead",
    response = classOf[Lead]
  )
  def getLead(@ApiParam(value = "The id of the case to retrieve") leadId: BSONObjectID) = Action.async{ req =>
    leadRepo.getT(leadId).map { maybeLead =>
      maybeLead.map { lead =>
        Ok(Json.toJson(lead))
      }.getOrElse(NotFound)
    }
  }
}
