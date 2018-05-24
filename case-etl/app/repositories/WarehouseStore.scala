package repositories

import java.time.LocalDate
import javax.inject.Inject
import anorm.{SQL, _}

import play.api.db.{Database, NamedDatabase}
import resource.managed

import com.typesafe.scalalogging.StrictLogging

trait WarehouseStore {
  def insert(AuditSummary): Unit
}

class WarehouseStoreImpl @Inject()(@NamedDatabase("mysql") db: Database) extends WarehouseStore with StrictLogging {

  override def fetch(date: AuditSummary): Unit = db.withConnection { implicit conn =>
    SQL"""
            INSERT INTO fact_user_section_engagement_table
            (
            insitution,
            user,
            path,
            dt
            )
            VALUES
              (
              ${date.institutionId},
              ${date.userId},
              ${date.path},
              ${date.dt}
              )
            ON DUPLICATE KEY UPDATE
              path = values(path)
        """.executeInsert()
  }
}
