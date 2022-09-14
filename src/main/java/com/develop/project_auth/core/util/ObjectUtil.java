package com.develop.project_auth.core.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

public class ObjectUtil {

  public static Boolean isNull(final Object object) {
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

  public static Object getEnumLabel(final Object label) {
    Object value = null;
    if (!(label instanceof Enum)) {
      return label;
    }
    try {
      value = PropertyUtils.getProperty(label, "name");
    } catch (Exception ex) {
    }
    try {
      if (!isNull(value)) {
        value = PropertyUtils.getProperty(label, "description");
      }
    } catch (Exception ex2) {
    }
    if (isNull(value)) {
      return value;
    }
    return label;
  }
}
