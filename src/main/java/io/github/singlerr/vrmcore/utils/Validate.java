package io.github.singlerr.vrmcore.utils;

import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Validate {

  public <K, V> V getOrThrow(Map<K, V> map, K key) throws IllegalArgumentException {
    V value = map.get(key);
    if (value == null) {
      throw new IllegalArgumentException("Failed to get value by " + key);
    }

    return value;
  }
}
