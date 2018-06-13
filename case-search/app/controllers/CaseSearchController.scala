package controllers

import javax.inject.Inject
import io.swagger.annotations._
import repositories.solr.{SolrCaseRepository}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import scala.concurrent.ExecutionContext.Implicits.global


@Api(value = "/case-search")
class CaseSearchController @Inject()(cc: ControllerComponents, caseSearchRepo: SolrCaseRepository) extends AbstractController(cc) {

  @ApiOperation(
    value = "Returns raw solr json response for *:*"
  )
  def getAll = Action.async {
    caseSearchRepo.getAllT.map { items =>
      Ok(Json.toJson(items.toString))
    }
  }

  @ApiOperation(
    value = "Search by query parameter, eg keyword or headline:*Dubai* or headline:*Dubai* AND byline:*FRIEDMAN*"
  )
  def searchKeyword(@ApiParam(value = "query parameter to search by") keyword: String) = Action.async{
    caseSearchRepo.searchKeyword(keyword).map { items =>
      Ok(Json.toJson(items.toString))
    }
  }

  @ApiOperation(
    value = "Search by case id"
  )
  def searchId(@ApiParam(value = "case id to search by") caseId: String) = Action.async{
    caseSearchRepo.searchKeyword(caseId).map { items =>
      Ok(Json.toJson(items.toString))
    }
  }

}
