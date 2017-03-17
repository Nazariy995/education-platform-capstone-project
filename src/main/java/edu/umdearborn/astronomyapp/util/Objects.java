package edu.umdearborn.astronomyapp.util;

public final class Objects {

  public static <T> T castIfBelongsToType(Object o, Class<T> type) {
    if (o != null && type.isAssignableFrom(o.getClass())) {
      return type.cast(o);
    }
    return null;
  }

}
