package edu.umdearborn.astronomyapp.util;

import java.util.List;

public final class ResultListUtil {
  
  public static boolean hasResult(List<?> result) {
   return result != null && !result.isEmpty(); 
  }
}
