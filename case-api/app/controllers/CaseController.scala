package controllers

import io.swagger.annotations._
import javax.inject.Inject
import models.{Case}
import repositories.mongo.{CaseRepository}
import reactivemongo.bson.BSONObjectID
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Api(value = "/cases")
class CaseController @Inject()(cc: ControllerComponents, caseRepo: CaseRepository) extends AbstractController(cc) {

  @ApiOperation(
    value = "Find all Case",
    response = classOf[Case],
    responseContainer = "List"
  )
  def getAllCases = Action.async {
    caseRepo.getAllCases.map { cases =>
      Ok(Json.toJson(cases))
    }
  }

  @ApiOperation(
    value = "Add a new case to the list",
    response = classOf[Void],
    code = 201
  )
  @ApiResponses(
    Array(
      new ApiResponse(code = 400, message = "Invalid case format")
    )
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        value = "The case to add, in Json Format",
        required = true,
        dataType = "models.Case",
        paramType = "body"
      )
    )
  )
  def createCase() = Action.async(parse.json) { req =>
    req.body.validate[Case].map { caseData =>
      caseRepo.addCase(caseData).map { _ =>
        Created
      }
    }.getOrElse(Future.successful(BadRequest("Invalid Case format")))
  }

  @ApiOperation(
    value = "Delete a case",
    response = classOf[Case]
  )
  def deleteCase(@ApiParam(value = "The id of the case to delete") caseId: BSONObjectID) = Action.async{ req =>
    caseRepo.deleteCase(caseId).map {
      case Some(caseid) => Ok(Json.toJson(caseid))
      case None => NotFound
    }
  }

  @ApiOperation(
    value = "Find a case",
    response = classOf[Case]
  )
  def getCase(@ApiParam(value = "The id of the case to retrieve") caseId: BSONObjectID) = Action.async{ req =>
    caseRepo.getCase(caseId).map { maybeCase =>
      maybeCase.map { `case` =>
        Ok(Json.toJson(`case`))
      }.getOrElse(NotFound)
    }
  }
}
