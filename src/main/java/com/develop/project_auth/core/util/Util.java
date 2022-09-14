package com.develop.project_auth.core.util;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class Util {

  public static Boolean isPositive(BigDecimal bigDecimal) {
    return !isNull(bigDecimal) && BigDecimal.ZERO.compareTo(bigDecimal) < 0;
  }

  public static Boolean isNull(BigDecimal bigDecimal) {
    return bigDecimal == null;
  }

  public static Boolean isPositive(Integer integer) {
    return !isNull(integer) && integer > 0;
  }

  public static Boolean isNull(Integer integer) {
    return integer == null;
  }

  public static Boolean isPositive(Long long1) {
    return !isNull(long1) && long1 > 0;
  }

  public static Boolean isNull(Long long1) {
    return long1 == null;
  }

  public static Boolean isNull(Object object) {
    return object == null;
  }

  public static Boolean isNewEntity(final Object object) {
    Long id = null;
    try {
      if (!isNull(object)) {
        id = (Long) FieldUtils.readField(object, "id", true);
      }
    } catch (IllegalAccessException ex) {
    }
    return !NumberUtil.isPositive(id);
  }

  public static Boolean isEmpty(String string) {
    return string == null || string.isEmpty();
  }

  public static Boolean isEmpty(List<?> list) {
    return list == null || list.isEmpty();
  }

  public static Boolean isEmpty(Object[] list) {
    return list == null || list.length == 0;
  }

  public static Boolean isBetween(ZonedDateTime compare, ZonedDateTime begin, ZonedDateTime end) {
    if (compare == null || begin == null || end == null) {
      return Boolean.FALSE;
    }
    return begin.compareTo(compare) <= 0 && end.compareTo(compare) >= 0;
  }

  public static Boolean isBetween(LocalDateTime compare, LocalDateTime begin, LocalDateTime end) {
    if (compare == null || begin == null || end == null) {
      return Boolean.FALSE;
    }
    return begin.compareTo(compare) <= 0 && end.compareTo(compare) >= 0;
  }

  public static Boolean isBetween(LocalDate compare, LocalDate begin, LocalDate end) {
    if (compare == null || begin == null || end == null) {
      return Boolean.FALSE;
    }
    return begin.compareTo(compare) <= 0 && end.compareTo(compare) >= 0;
  }

  public static Boolean isBetween(LocalTime compare, LocalTime begin, LocalTime end) {
    if (compare == null || begin == null || end == null) {
      return Boolean.FALSE;
    }
    return begin.compareTo(compare) <= 0 && end.compareTo(compare) >= 0;
  }

  public static <T> Page<T> listToPage(List<T> list, Pageable pageable) {
    if (isNull(pageable)) {
      pageable = PageRequest.of(0, 10);
    }
    if (!isEmpty(list)) {
      int fromIndex = (int) pageable.getOffset();
      int toIndex = Math.min(fromIndex + pageable.getPageSize(), list.size());
      return new PageImpl<T>(list.subList(fromIndex, toIndex), pageable, list.size());
    }
    return new PageImpl<T>(new ArrayList<>(), pageable, 0);
  }

  public static String getBigDecimalFormatted(BigDecimal bigDecimal, int decimalPlaces,
      boolean currency) {
    NumberFormat numberFormat = NumberFormat.getNumberInstance(LocaleContextHolder.getLocale());
    if (currency) {
      numberFormat = NumberFormat.getCurrencyInstance(LocaleContextHolder.getLocale());
    }
    numberFormat.setGroupingUsed(true);
    numberFormat.setMinimumFractionDigits(decimalPlaces);
    numberFormat.setMaximumFractionDigits(decimalPlaces);
    return numberFormat.format(bigDecimal);
  }

  public static BigDecimal getBigDecimal(BigDecimal bigDecimal) {
    if (isNull(bigDecimal)) {
      return BigDecimal.ZERO;
    }
    return bigDecimal;
  }

  public static Integer getInteger(Integer integer) {
    if (isNull(integer)) {
      return 0;
    }
    return integer;
  }

  public static Boolean getBoolean(Boolean bool) {
    if (isNull(bool)) {
      return Boolean.FALSE;
    }
    return bool;
  }

  public static <T> List<T> getList(List<T> list) {
    if (isNull(list)) {
      return new ArrayList<>();
    }
    return list;
  }

  public static Object getEnumLabel(Object label) {
    Object value = null;
    if (label instanceof Enum) {
      try {
        value = PropertyUtils.getProperty(label, "name");
      } catch (Exception e) {
      }
      try {
        if (!Util.isNull(value)) {
          value = PropertyUtils.getProperty(label, "description");
        }
      } catch (Exception e) {
      }
      if (Util.isNull(value)) {
        return value;
      }
      return label;
    }
    return label;
  }

  public static Thread getThreadByName(String name) {
    Optional<Thread> parameter = Thread.getAllStackTraces().keySet().stream()
        .filter((Thread t) -> t.getName().equals(name)).findFirst();
    if (parameter.isPresent()) {
      return parameter.get();
    }
    return null;
  }

  public static ZonedDateTime getZonedDateTime(Date date) {
    if (isNull(date)) {
      return ZonedDateTime.now();
    }
    return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  public static String normalize(String string) {
    string = Normalizer.normalize(string, Normalizer.Form.NFD);
    return string.replaceAll("[^\\p{ASCII}]", "");
  }

}
