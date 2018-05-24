package repositories

import java.time.LocalDate

import anorm.{Macro, RowParser}
import play.api.libs.json.Json

case class AuditSummary(
  institutionId: String,
  userId: String,
  path: String,
  dt: LocalDate
)

object AuditSummary {
  val parser: RowParser[AuditSummary] = Macro.namedParser[AuditSummary]
  implicit val jsonFormat = Json.format[AuditSummary]
}
