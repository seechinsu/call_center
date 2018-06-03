package controllers

import javax.inject.Inject
import io.swagger.annotations._
import models.{Narrative}
import repositories.mongo.{NarrativeRepository}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import reactivemongo.bson.BSONObjectID

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Api(value = "/narrative")
class NarrativeController @Inject()(cc: ControllerComponents, narrativeRepo: NarrativeRepository) extends AbstractController(cc) {

  @ApiOperation(
    value = "Find all Narrative",
    response = classOf[Narrative],
    responseContainer = "List"
  )
  def getAllNarratives = Action.async {
    narrativeRepo.getAll.map { narrative =>
      Ok(Json.toJson(narrative))
    }
  }

  @ApiOperation(
    value = "Add a new narrative to the list",
    response = classOf[Void],
    code = 201
  )
  @ApiResponses(
    Array(
      new ApiResponse(code = 400, message = "Invalid narrative format")
    )
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        value = "The narrative to add, in Json Format",
        required = true,
        dataType = "models.Narrative",
        paramType = "body"
      )
    )
  )
  def createNarrative() = Action.async(parse.json) { req =>
    req.body.validate[Narrative].map { narrativeData =>
      narrativeRepo.addNarrative(narrativeData).map { _ =>
        Created
      }
    }.getOrElse(Future.successful(BadRequest("Invalid Narrative format")))
  }

  @ApiOperation(
    value = "Delete a narrative",
    response = classOf[Narrative]
  )
  def deleteNarrative(@ApiParam(value = "The id of the narrative to delete") narrativeId: BSONObjectID) = Action.async{ req =>
    narrativeRepo.deleteNarrative(narrativeId).map {
      case Some(narrative) => Ok(Json.toJson(narrative))
      case None => NotFound
    }
  }

  @ApiOperation(
    value = "Find an narrative by id",
    response = classOf[Narrative]
  )
  def getNarrative(@ApiParam(value = "The id of the narrative to retrieve") narrativeId: BSONObjectID) = Action.async{ req =>
    narrativeRepo.getNarrative(narrativeId).map { maybeNarrative =>
      maybeNarrative.map { narrative =>
        Ok(Json.toJson(narrative))
      }.getOrElse(NotFound)
    }
  }
}


