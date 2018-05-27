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

  val solr = AsyncSolrClient(s"${config.underlying.getString("solrEndpoint")}/case")

  def getCase(id: BSONObjectID): Future[Option[Case]] = {
    solr.query(new SolrQuery(s"id:${id.stringify}")).map(x => x.getBeans(classOf[Case]).asScala.headOption)
  }

  def addCase(`case`: Case): Future[UpdateResponse] = {
    solr.addBean(Some("case"),`case`)
    solr.commit(Some("case"))
  }

  def updateCase(id: BSONObjectID, `case`: Case): Future[UpdateResponse] = {
    deleteCase(id).flatMap(_ => addCase(`case`))

  }

  def deleteCase(id: BSONObjectID): Future[UpdateResponse] = {
    solr.deleteById(Some("case"), id.stringify)
  }

}
