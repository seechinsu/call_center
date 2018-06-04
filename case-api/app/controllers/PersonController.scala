package controllers


import io.swagger.annotations._
import javax.inject.Inject
import models.Person
import repositories.mongo.PersonRepository
import reactivemongo.bson.BSONObjectID
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Api(value = "/people")
class PersonController @Inject()(cc: ControllerComponents, personRepo: PersonRepository) extends AbstractController(cc) {

  @ApiOperation(
    value = "Find all People",
    response = classOf[Person],
    responseContainer = "List"
  )
  def getAllPeople = Action.async {
    personRepo.getAll.map { people =>
      Ok(Json.toJson(people))
    }
  }


  @ApiOperation(
    value = "Add a new person to the list",
    response = classOf[Void],
    code = 201
  )
  @ApiResponses(
    Array(
      new ApiResponse(code = 400, message = "Invalid person format")
    )
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        value = "The person to add, in Json Format",
        required = true,
        dataType = "models.Person",
        paramType = "body"
      )
    )
  )
  def createPerson() = Action.async(parse.json) { req =>
    req.body.validate[Person].map { personData =>
      personRepo.addPerson(personData).map { _ =>
        Created
      }
    }.getOrElse(Future.successful(BadRequest("Invalid Person format")))
  }

  @ApiOperation(
    value = "Delete a Person",
    response = classOf[Person]
  )
  def deletePerson(@ApiParam(value = "The id of the person to delete") personId: BSONObjectID) = Action.async{ req =>
    personRepo.deletePerson(personId).map {
      case Some(person) => Ok(Json.toJson(person))
      case None => NotFound
    }
  }

  @ApiOperation(
    value = "Find an person by id",
    response = classOf[Person]
  )
  def getPerson(@ApiParam(value = "The id of the person to retrieve") personId: BSONObjectID) = Action.async{ req =>
    personRepo.getPerson(personId).map { maybePerson =>
      maybePerson.map { person =>
        Ok(Json.toJson(person))
      }.getOrElse(NotFound)
    }
  }
}
