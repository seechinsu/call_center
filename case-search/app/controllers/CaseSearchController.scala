package controllers

import javax.inject.Inject
import io.swagger.annotations._
import repositories.solr.{SolrCaseRepository}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import scala.concurrent.ExecutionContext.Implicits.global


@Api(value = "/search")
class CaseSearchController @Inject()(cc: ControllerComponents, caseSearchRepo: SolrCaseRepository) extends AbstractController(cc) {

  @ApiOperation(
    value = "Test solr query connection"
  )
  def getAll = Action.async {
    caseSearchRepo.getAll.map { items =>
      Ok(Json.toJson(items.toString))
    }
  }

  @ApiOperation(
    value = "Search by keyword"
  )
  def searchKeyword(@ApiParam(value = "Keyword to search by") keyword: String) = Action.async{
    caseSearchRepo.searchKeyword(keyword).map { items =>
      Ok(Json.toJson(items.toString))
    }
  }
}
