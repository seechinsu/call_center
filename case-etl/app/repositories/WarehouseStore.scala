package repositories

import java.time.LocalDate
import javax.inject.Inject
import anorm.{SQL, _}

import play.api.db.{Database, NamedDatabase}
import resource.managed

import com.typesafe.scalalogging.StrictLogging

import java.util.Date

trait WarehouseStore {
  def insert(item: AuditSummary): Unit
}

class WarehouseStoreImpl @Inject()(@NamedDatabase("mysql") db: Database) extends WarehouseStore with StrictLogging {

  override def insert(item: AuditSummary): Unit = db.withConnection { implicit conn =>
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
              ${item.institutionId},
              ${item.userId},
              ${item.path},
              ${new Date(item.dt.toEpochDay)}
              )
            ON DUPLICATE KEY UPDATE
              path = values(path)
        """.executeInsert()
  }
}
