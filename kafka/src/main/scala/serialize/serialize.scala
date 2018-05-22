import akka.util.ByteString
import com.typesafe.scalalogging.LazyLogging
import org.json4s.{CustomSerializer, JNull, JString, MappingException, NoTypeHints}
import org.json4s.ext._
import org.json4s.JsonAST.JInt
import org.json4s.ext._
import org.json4s.native.Serialization

import java.net.URI
import java.time.{DayOfWeek, Instant, ZoneId}


package object serialize extends LazyLogging {

  // https://github.com/json4s/json4s see "Serializing non-supported types"
  implicit val formats = Serialization.formats(NoTypeHints) +
    EventJavaDayOfWeekSerializer +
    EventJavaInstantSerializer +
    EventJavaZoneIdSerializer +
    EventUriSerializer ++
    List(
      JDurationSerializer,
      JYearSerializer,
      JLocalDateTimeSerializer,
      JLocalDateSerializer(),
      JLocalTimeSerializer(),
      JPeriodSerializer(),
      JYearMonthSerializer(),
      JMonthDaySerializer()
    )

  def serialize[T](data: T): String = Serialization.write(data.asInstanceOf[AnyRef])

  def deserialize[T: Manifest](record: ByteString): Option[T] = {
    try {
      val deserialized: T = Serialization.read[T](record.utf8String)
      Some(deserialized)
    } catch {
      case e: MappingException =>
        logger.warn("bad json message", e)
        None
    }
  }
}


case object EventJavaDayOfWeekSerializer extends CustomSerializer[DayOfWeek](
  format => ( {
    case JString(s) => DayOfWeek.valueOf(s)
    case JNull => null
  }, {
    case d: DayOfWeek => JString(d.toString)
  })
)

case object EventJavaInstantSerializer extends CustomSerializer[Instant](
  format => ( {
    case JInt(i) => Instant.ofEpochMilli(i.longValue())
    case JString(s) => Instant.parse(s)
    case JNull => null
  }, {
    case i: Instant => JString(i.toString)
  })
)

case object EventJavaZoneIdSerializer extends CustomSerializer[ZoneId](
  format => ( {
    case JString(s) => ZoneId.of(s)
    case JNull => null
  }, {
    case z: ZoneId => JString(z.getId)
  })
)

case object EventUriSerializer extends CustomSerializer[URI](
  format => ( {
    case JString(s) => new URI(s)
    case JNull => null
  }, {
    case u: URI => JString(u.toString)
  })
)
