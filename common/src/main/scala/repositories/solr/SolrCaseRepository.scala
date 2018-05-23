package repositories.solr

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import scala.concurrent.ExecutionContext.Implicits.global
import io.ino.solrs.AsyncSolrClient
import io.ino.solrs.future.ScalaFutureFactory.Implicit
import org.apache.solr.client.solrj.response.QueryResponse
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.common.SolrDocumentList
import scala.concurrent.{Future}


class SolrCaseRepository @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getAll: Future[SolrDocumentList] = {
    val solr = AsyncSolrClient("http://localhost:8983/solr/news")
    val response: Future[QueryResponse] = solr.query(new SolrQuery("*:*").setParam("wt","json"))
    response.map {
      qr => qr.getResults()
    }
  }

  def searchKeyword(keyword: String): Future[SolrDocumentList] = {
    val solr = AsyncSolrClient("http://localhost:8983/solr/news")
    val response: Future[QueryResponse] = solr.query(new SolrQuery(keyword).setParam("wt","json"))
    response.map {
      qr => qr.getResults()
    }
  }

}
