package repositories.solr

import javax.inject.Inject
import play.api.Configuration

class SolrCaseRepository @Inject()(implicit config: Configuration) extends SolrBaseRepository("case"){

}
