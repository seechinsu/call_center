package repositories.solr

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext.Implicits.global
import io.ino.solrs.AsyncSolrClient
import io.ino.solrs.future.ScalaFutureFactory.Implicit
import models.Case
import org.apache.solr.client.solrj.response.{QueryResponse, UpdateResponse}
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.common.SolrDocumentList
import play.api.Configuration
import reactivemongo.bson.BSONObjectID

import scala.collection.JavaConverters._
import scala.concurrent.Future


class SolrCaseRepository @Inject()(cc: ControllerComponents, config: Configuration) extends AbstractController(cc) {

  //val solr = AsyncSolrClient(s"${config.underlying.getString("solrEndpoint")}/search")

  val solr = AsyncSolrClient("http://localhost:8983/solr/news")

  def getAll: Future[SolrDocumentList] = {
    solr.query(new SolrQuery("*:*").setParam("wt","json")).map {
      qr => qr.getResults()
    }
  }

  def searchKeyword(keyword: String): Future[SolrDocumentList] = {
    solr.query(new SolrQuery(keyword).setParam("wt","json")).map {
      qr => qr.getResults()
    }
  }

  def getCase(id: BSONObjectID): Future[Option[Case]] = {
    solr.query(new SolrQuery(s"id:${id.stringify}")).map(x => x.getBeans(classOf[Case]).asScala.headOption)
  }
}
