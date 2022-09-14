
package com.develop.project_auth.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Date;


public class DateUtil {

  public static Boolean isBetween(final ZonedDateTime compare, final ZonedDateTime begin,
      final ZonedDateTime end) {
    if (compare == null || begin == null || end == null) {
      return Boolean.FALSE;
    }
    return begin.compareTo((ChronoZonedDateTime<?>) compare) <= 0
        && end.compareTo((ChronoZonedDateTime<?>) compare) >= 0;
  }

  public static Boolean isBetween(final LocalDateTime compare, final LocalDateTime begin,
      final LocalDateTime end) {
    if (compare == null || begin == null || end == null) {
      return Boolean.FALSE;
    }
    return begin.compareTo((ChronoLocalDateTime<?>) compare) <= 0
        && end.compareTo((ChronoLocalDateTime<?>) compare) >= 0;
  }

  public static Boolean isBetween(final LocalDate compare, final LocalDate begin,
      final LocalDate end) {
    if (compare == null || begin == null || end == null) {
      return Boolean.FALSE;
    }
    return begin.compareTo((ChronoLocalDate) compare) <= 0
        && end.compareTo((ChronoLocalDate) compare) >= 0;
  }

  public static Boolean isBetween(final LocalTime compare, final LocalTime begin,
      final LocalTime end) {
    if (compare == null || begin == null || end == null) {
      return Boolean.FALSE;
    }
    return begin.compareTo(compare) <= 0 && end.compareTo(compare) >= 0;
  }

  public static ZonedDateTime getZonedDateTime(final Date date) {
    if (ObjectUtil.isNull(date)) {
      return ZonedDateTime.now();
    }
    return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  public static OffsetDateTime getOffsetDateTimeOfBrazil(LocalDate date) {
    String defaultZone = "America/Sao_Paulo";
    return date.atStartOfDay(ZoneId.of(defaultZone)).toOffsetDateTime();
  }

  public static LocalDateTime convertDateToZone(LocalDateTime date, ZoneId zoneId) {
    ZoneId currentZone = ZoneId.of("UTC");

    if (ObjectUtil.isNull(zoneId)) {
      zoneId = ZoneId.of("America/Sao_Paulo");
    }

    LocalDateTime newDateTime = date.atZone(currentZone)
        .withZoneSameInstant(zoneId)
        .toLocalDateTime();

    return newDateTime;
  }
}
