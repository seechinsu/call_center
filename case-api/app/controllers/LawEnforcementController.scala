package controllers

import javax.inject.Inject
import io.swagger.annotations._
import models.{LawEnforcement}
import repositories.mongo.{LawEnforcementRepository}

import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import reactivemongo.bson.BSONObjectID

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Api(value = "/lea")
class LawEnforcementController @Inject()(cc: ControllerComponents, leaRepo: LawEnforcementRepository) extends AbstractController(cc) {

  @ApiOperation(
    value = "Find all law enforcement agents",
    response = classOf[LawEnforcement],
    responseContainer = "List"
  )
  def getAllLea = Action.async {
    leaRepo.getAllTs.map { lea =>
      Ok(Json.toJson(lea))
    }
  }


  @ApiOperation(
    value = "Add a new law enforcement agent to the list",
    response = classOf[Void],
    code = 201
  )
  @ApiResponses(
    Array(
      new ApiResponse(code = 400, message = "Invalid LawEnforcement format")
    )
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        value = "The law enforcement agent to add, in Json Format",
        required = true,
        dataType = "models.LawEnforcement",
        paramType = "body"
      )
    )
  )
  def createLawEnforcement() = Action.async(parse.json) { req =>
    req.body.validate[LawEnforcement].map { leaData =>
      leaRepo.addT(leaData).map { _ =>
        Created
      }
    }.getOrElse(Future.successful(BadRequest("Invalid LawEnforcement format")))
  }

  @ApiOperation(
    value = "Delete a law enforcement agent",
    response = classOf[LawEnforcement]
  )
  def deleteLawEnforcement(@ApiParam(value = "The id of the law enforcement agent to delete") leaId: BSONObjectID) = Action.async{ req =>
    leaRepo.deleteT(leaId).map {
      case Some(lea) => Ok(Json.toJson(lea))
      case None => NotFound
    }
  }

  @ApiOperation(
    value = "Find an lea by id",
    response = classOf[LawEnforcement]
  )
  def getLawEnforcement(@ApiParam(value = "The id of the lea to retrieve") leaId: BSONObjectID) = Action.async{ req =>
    leaRepo.getT(leaId).map { maybeLawEnforcement =>
      maybeLawEnforcement.map { lea =>
        Ok(Json.toJson(lea))
      }.getOrElse(NotFound)
    }
  }
}

