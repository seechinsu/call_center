package repositories.solr

import models.Case
import reactivemongo.bson.BSONObjectID

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import io.ino.solrs.AsyncSolrClient
import io.ino.solrs.future.ScalaFutureFactory.Implicit

import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.response.UpdateResponse
import play.api.Configuration

import scala.collection.JavaConverters._


class SolrCaseRepository @Inject()(config: Configuration)(implicit ec: ExecutionContext) {

  import Case._

  val solr = AsyncSolrClient(s"${config.underlying.getString("solrEndpoint")}/case")

  def getAll: Future[Seq[Case]] = {
    solr.query(new SolrQuery("*:*")).map(x => x.getBeans(classOf[Case]).asScala)
  }

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
