package repositories

import java.time.LocalDate
import javax.inject.Inject
import anorm._

import play.api.db.{Database, NamedDatabase}
import resource.managed

import com.typesafe.scalalogging.StrictLogging

trait PrestoStore {
  def fetch(date: LocalDate): Seq[AuditSummary]
}

class PrestoStoreImpl @Inject()(@NamedDatabase("presto") db: Database) extends PrestoStore with StrictLogging {

  override def fetch(date: LocalDate): Seq[AuditSummary] = db.withConnection { implicit conn =>

    val result = conn.createStatement.executeQuery(
      s"""
          SELECT
            meta.tenant_id as institutionId,
            meta.user_id as userId,
            request.path as path,
            cast(dt as timestamp) AS dt
          from hive.lib_events.audit_access
          where dt = '$date'
      """
    )

    SqlQueryResult(managed(result)).as(AuditSummary.parser.*)
  }
}
