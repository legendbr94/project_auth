
package com.develop.project_auth.core.util;

public class BooleanUtil {

  public static Boolean getBoolean(final Boolean bool) {
    if (ObjectUtil.isNull(bool)) {
      return Boolean.FALSE;
    }
    return bool;
  }
}
