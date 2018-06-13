package repositories.solr

import io.ino.solrs.AsyncSolrClient
import io.ino.solrs.future.ScalaFutureFactory.Implicit
import reactivemongo.bson.BSONObjectID
import repositories.solr.utils.SolrUtils

import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.response.UpdateResponse
import org.apache.solr.common.SolrDocumentList
import play.api.Configuration
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class SolrBaseRepository[T <: Object] @Inject()(name: String)(implicit config: Configuration) {

  val solr = AsyncSolrClient(s"${config.underlying.getString("solrEndpoint")}/${name}")

  def getAllT: Future[SolrDocumentList] = {
    solr.query(new SolrQuery("*:*").setParam("wt","json")).map {
      qr => qr.getResults()
    }
  }

  def searchKeyword(keyword: String): Future[SolrDocumentList] = {
    solr.query(new SolrQuery(keyword).setParam("wt","json")).map {
      qr => qr.getResults()
    }
  }

  def getT(id: BSONObjectID): Future[SolrDocumentList] = {
    solr.query(new SolrQuery(s"id:${id.stringify}")).map {
      qr => qr.getResults()
    }
  }

  def addT(id: BSONObjectID, t: T): Future[UpdateResponse] = {
      solr.addDoc(None,SolrUtils.autoMapToSolrInputDoc("id", id.stringify, t, null))
    solr.commit(Some(name))
  }

  def updateT(id: BSONObjectID, t: T): Future[UpdateResponse] = {
    deleteT(id).flatMap(_ => addT(id,t))

  }

  def deleteT(id: BSONObjectID): Future[UpdateResponse] = {
    solr.deleteById(Some(name), id.stringify)
  }
}
