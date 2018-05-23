package controllers

import client.KafkaProducerClient
import io.swagger.annotations._
import models.Case
import reactivemongo.bson.BSONObjectID
import repositories.mongo.CaseRepository

import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


@Api(value = "/cases")
class CaseController @Inject()(cc: ControllerComponents, caseRepo: CaseRepository, kafka: KafkaProducerClient) extends AbstractController(cc) {

  @ApiOperation(
    value = "Find all Cases",
    response = classOf[Case],
    responseContainer = "List"
  )
  def getAllCases = Action.async {
    caseRepo.getAll.map{ cases =>
      Ok(Json.toJson(cases))
    }
  }


  @ApiOperation(
    value = "Get a Case",
    response = classOf[Case]
  )
  @ApiResponses(Array(
      new ApiResponse(code = 404, message = "Case not found")
    )
  )
  def getCase(@ApiParam(value = "The id of the Case to fetch") caseId: BSONObjectID) = Action.async{ req =>
    caseRepo.getCase(caseId).map{ maybeCase =>
      maybeCase.map{ `case` =>
        Ok(Json.toJson(`case`))
      }.getOrElse(NotFound)
    }
  }

  @ApiOperation(
    value = "Add a new Case to the list",
    response = classOf[Void],
    code = 201
  )
  @ApiResponses(Array(
      new ApiResponse(code = 400, message = "Invalid Case format")
    )
  )
  @ApiImplicitParams(Array(
      new ApiImplicitParam(value = "The Case to add, in Json Format", required = true, dataType = "models.Case", paramType = "body")
    )
  )
  def createCase() = Action.async(parse.json){ req =>
    req.body.validate[Case].map{ `case` =>
      caseRepo.addCase(`case`).map{ _ =>
        Created
      }
    }.getOrElse(Future.successful(BadRequest("Invalid Case format")))
  }

  @ApiOperation(
    value = "Update a Case",
    response = classOf[Case]
  )
  @ApiResponses(Array(
      new ApiResponse(code = 400, message = "Invalid Case format")
    )
  )
  @ApiImplicitParams(Array(
      new ApiImplicitParam(value = "The updated Case, in Json Format", required = true, dataType = "models.Case", paramType = "body")
    )
  )
  def updateCase(@ApiParam(value = "The id of the Case to update")
                 caseId: BSONObjectID) = Action.async(parse.json){ req =>
    req.body.validate[Case].map{ c =>
      caseRepo.updateCase(caseId, c).map {
        case Some(x) => Ok(Json.toJson(x))
        case None => NotFound
      }
    }.getOrElse(Future.successful(BadRequest("Invalid Json")))
  }

  @ApiOperation(
    value = "Delete a Case",
    response = classOf[Case]
  )
  def deleteCase(@ApiParam(value = "The id of the Case to delete") caseId: BSONObjectID) = Action.async{ req =>
    caseRepo.deleteCase(caseId).map {
      case Some(x) => Ok(Json.toJson(x))
      case None => NotFound
    }
  }
}
