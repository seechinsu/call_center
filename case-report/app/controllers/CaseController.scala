package controllers

import io.swagger.annotations._

import javax.inject.Inject
import models.Case
import reactivemongo.bson.BSONObjectID
import report.{CompositeService, ReportService}

import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

@Api(value = "/cases")
class CaseController @Inject()(
  cc: ControllerComponents,
  reportService: ReportService,
  compositeService: CompositeService
)(implicit executionContext: ExecutionContext) extends AbstractController(cc) {

  @ApiOperation(
    value = "Find all Case",
    response = classOf[Case],
    responseContainer = "List"
  )
  def getAllCases = Action.async { implicit request => Future.successful(Ok)
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
  def createCase() = Action.async(parse.json) { req => Future.successful(Ok)
  }

  @ApiOperation(
    value = "Delete a case",
    response = classOf[Case]
  )
  def deleteCase(@ApiParam(value = "The id of the case to delete") caseId: BSONObjectID) = Action.async { req =>
    Future.successful(Ok)
  }

  @ApiOperation(
    value = "Find a case",
    response = classOf[Case]
  )
  def getCase(@ApiParam(value = "The id of the case to retrieve") caseId: BSONObjectID) = Action.async { req =>
    Future.successful(Ok)
  }
}
