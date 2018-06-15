package repositories.solr

import models.Case

import javax.inject.Inject
import play.api.Configuration

class SolrCaseRepository @Inject()(implicit config: Configuration) extends SolrBaseRepository[Case]("case"){

}
