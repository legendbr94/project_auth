package com.develop.project_auth.core.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class DataStructureUtil {

  public static Boolean isEmpty(final List<?> list) {
    return list == null || list.isEmpty();
  }

  public static Boolean isEmpty(final Object[] list) {
    return list == null || list.length == 0;
  }

  public static <T> List<T> getList(final List<T> list) {
    if (ObjectUtil.isNull(list)) {
      return new ArrayList<T>();
    }
    return list;
  }

  public static <T> Page<T> listToPage(final List<T> list, Pageable pageable) {
    if (ObjectUtil.isNull(pageable)) {
      pageable = (Pageable) PageRequest.of(0, 10);
    }
    if (!isEmpty(list)) {
      final int fromIndex = (int) pageable.getOffset();
      final int toIndex = Math.min(fromIndex + pageable.getPageSize(), list.size());
      return (Page<T>) new PageImpl((List) list.subList(fromIndex, toIndex), pageable,
          (long) list.size());
    }
    return (Page<T>) new PageImpl((List) new ArrayList(), pageable, 0L);
  }
}
