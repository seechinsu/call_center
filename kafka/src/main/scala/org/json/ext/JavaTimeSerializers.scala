package org.json4s.ext

import org.json4s.JsonAST.{JNull, JString}
import org.json4s._

import java.time._
import java.util.Date


object JavaTimeSerializers {
  def all = List(
    JDurationSerializer,
    JInstantSerializer,
    JYearSerializer,
    JLocalDateTimeSerializer,
    JLocalDateSerializer(),
    JLocalTimeSerializer(),
    JPeriodSerializer(),
    JYearMonthSerializer(),
    JMonthDaySerializer()
  )
}

case object JDurationSerializer extends CustomSerializer[Duration](
  format => ( {
    case JInt(d) => Duration.ofMillis(d.toLong)
    case JNull => null
  }, {
    case d: Duration => JInt(d.toMillis)
  }
  )
)

case object JInstantSerializer extends CustomSerializer[Instant](
  format => ( {
    case JInt(d) => Instant.ofEpochMilli(d.toLong)
    case JNull => null
  }, {
    case i: Instant => JInt(i.toEpochMilli)
  }
  )
)

case object JYearSerializer extends CustomSerializer[Year](
  format => ( {
    case JInt(n) => Year.of(n.toInt)
    case JNull => null
  }, {
    case y: Year => JInt(y.getValue)
  }
  )
)

case object JLocalDateTimeSerializer extends CustomSerializer[LocalDateTime](
  format => ( {
    case JString(s) =>
      LocalDateTime.parse(s)
    case JNull => null
  }, {
    case ldt: LocalDateTime =>
      JString(ldt.toString)
  }
  )
)

private[ext] case class _JLocalDate(year: Int, month: Int, day: Int)

object JLocalDateSerializer {
  def apply() = new ClassSerializer(
    new ClassType[LocalDate, _JLocalDate]() {
      def unwrap(ld: _JLocalDate)(implicit format: Formats) =
        LocalDate.of(ld.year, ld.month, ld.day)

      def wrap(ld: LocalDate)(implicit format: Formats) =
        _JLocalDate(ld.getYear, ld.getMonthValue, ld.getDayOfMonth)
    }
  )
}

private[ext] case class _JLocalTime(hour: Int, minute: Int, second: Int, nanoOfSecond: Int)

object JLocalTimeSerializer {
  def apply() = new ClassSerializer(
    new ClassType[LocalTime, _JLocalTime]() {
      def unwrap(lt: _JLocalTime)(implicit format: Formats) =
        LocalTime.of(lt.hour, lt.minute, lt.second, lt.nanoOfSecond)

      def wrap(lt: LocalTime)(implicit format: Formats) =
        _JLocalTime(lt.getHour, lt.getMinute, lt.getSecond, lt.getNano)
    }
  )
}

private[ext] case class _JPeriod(years: Int, months: Int, days: Int)

object JPeriodSerializer {
  def apply() = new ClassSerializer(
    new ClassType[Period, _JPeriod]() {
      def unwrap(p: _JPeriod)(implicit format: Formats) = Period.of(p.years, p.months, p.days)

      def wrap(p: Period)(implicit format: Formats) = _JPeriod(p.getYears, p.getMonths, p.getDays)
    }
  )
}

private[ext] case class _JYearMonth(year: Int, month: Int)

object JYearMonthSerializer {
  def apply() = new ClassSerializer(
    new ClassType[YearMonth, _JYearMonth]() {
      def unwrap(ym: _JYearMonth)(implicit format: Formats) = YearMonth.of(ym.year, ym.month)

      def wrap(ym: YearMonth)(implicit format: Formats) = _JYearMonth(ym.getYear, ym.getMonthValue)
    }
  )
}

private[ext] case class _JMonthDay(month: Int, dayOfMonth: Int)

object JMonthDaySerializer {
  def apply() = new ClassSerializer(
    new ClassType[MonthDay, _JMonthDay]() {
      def unwrap(md: _JMonthDay)(implicit format: Formats) = MonthDay.of(md.month, md.dayOfMonth)

      def wrap(md: MonthDay)(implicit format: Formats) = _JMonthDay(md.getMonthValue, md.getDayOfMonth)
    }
  )
}

object JavaDateConverters {

  implicit class ExtendedLocalDateTime(val ldt: LocalDateTime) extends AnyVal {
    def toDate: Date = {
      val instant = ldt.atZone(ZoneId.systemDefault).toInstant
      Date.from(instant)
    }
  }

  implicit class ExtendedDate(val d: Date) extends AnyVal {
    def toLocalDateTime: LocalDateTime =
      LocalDateTime.ofInstant(d.toInstant, ZoneId.systemDefault)
  }

}
