package com.develop.project_auth.core.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

  public static class Entry<K, V> {

    private Map<K, V> map;

    public Entry() {
      this.map = new HashMap<>();
    }

    public static <K, V> Entry<K, V> instance() {
      return new Entry<>();
    }

    public Entry<K, V> put(K k, V v) {
      this.map.put(k, v);
      return this;
    }

    public Map<K, V> build() {
      return this.map;
    }


  }
}
