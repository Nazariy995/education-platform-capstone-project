package edu.umdearborn.astronomyapp.util.constants;

public final class UrlConstants {

  public static final String ANY_SUB_DIR_PATTERN = "/**";

  public static final String[] PUBLIC_PATH_PATTERNS = {"/", "/index.html", "/app.js", "/app.css",
      "/js" + ANY_SUB_DIR_PATTERN, "/views" + ANY_SUB_DIR_PATTERN, "/{[path:[^\\.]*}"};

  public static final String REST_PATH_PREFIX = "/rest";

  public static final String   ADMIN_PATH          = "/admin";
  public static final String[] ADMIN_PATH_PATTERNS =
      {ADMIN_PATH + ANY_SUB_DIR_PATTERN, REST_PATH_PREFIX + ADMIN_PATH + ANY_SUB_DIR_PATTERN};

  public static final String   INSTRUCTOR_PATH          = "/instructor";
  public static final String[] INSTRUCTOR_PATH_PATTERNS = {INSTRUCTOR_PATH + ANY_SUB_DIR_PATTERN,
      REST_PATH_PREFIX + INSTRUCTOR_PATH + ANY_SUB_DIR_PATTERN};

  public static final String   STUDENT_PATH          = "/student";
  public static final String[] STUDENT_PATH_PATTERNS =
      {STUDENT_PATH + ANY_SUB_DIR_PATTERN, REST_PATH_PREFIX + STUDENT_PATH + ANY_SUB_DIR_PATTERN};

  public static final String LOGIN_PATH           = "/login";
  public static final String LOGIN_FAILURE_PATH   = LOGIN_PATH + "?error";
  public static final String LOGOUT_PATH          = "/logout";
  public static final String LOGOUT_SUCCESS_PATH  = LOGIN_PATH + "?logout";
  public static final String SESSION_EXPIRED_PATH = LOGIN_PATH + "?session-expired";
  public static final String SESSION_INVALID_PATH = LOGIN_PATH + "?session-invalid";

}
