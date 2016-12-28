package edu.umdearborn.astronomyapp.util.constants;

public final class UrlConstants {

  public static final String[] PUBLIC_PATH_PATTERNS = {"/", "/home", "/index", "**/*.*", "/public/**"};

  public static final String ADMIN_PATH         = "/admin";
  public static final String ADMIN_PATH_PATTERN = ADMIN_PATH + "/**";

  public static final String INSTRUCTOR_PATH         = "/instructor";
  public static final String INSTRUCTOR_PATH_PATTERN = INSTRUCTOR_PATH + "/**";

  public static final String STUDENT_PATH         = "/student";
  public static final String STUDENT_PATH_PATTERN = STUDENT_PATH + "/**";

  public static final String LOGIN_PATH           = "/login";
  public static final String LOGIN_FAILURE_PATH   = LOGIN_PATH + "?error";
  public static final String LOGOUT_PATH          = "/logout";
  public static final String LOGOUT_SUCCESS_PATH  = LOGIN_PATH + "?logout";
  public static final String SESSION_EXPIRED_PATH = LOGIN_PATH + "?session-expired";
  public static final String SESSION_INVALID_PATH = LOGIN_PATH + "?session-invalid";

}
