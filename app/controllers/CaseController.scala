package controllers

import io.swagger.annotations._
import javax.inject.Inject
import models.JsonFormats._
import models.{Case, CaseRepository, Todo, TodoRepository}
import reactivemongo.bson.BSONObjectID

import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Riccardo Sirigu on 10/08/2017.
  */
@Api(value = "/cases")
class CaseController @Inject()(cc: ControllerComponents, caseRepo: CaseRepository) extends AbstractController(cc) {

  @ApiOperation(
    value = "Find all Case",
    response = classOf[Case],
    responseContainer = "List"
  )
  def getAllCases = Action.async {
    caseRepo.getAll.map { x =>
      Ok(Json.toJson(x))
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
      caseRepo.add(caseData).map { _ =>
        Created
      }
    }.getOrElse(Future.successful(BadRequest("Invalid Case format")))
  }
}
