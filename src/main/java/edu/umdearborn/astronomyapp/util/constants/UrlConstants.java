package edu.umdearborn.astronomyapp.util.constants;

public final class UrlConstants {

  public static final String[] PUBLIC_PATH_PATTERNS = {"/", "/login", "/index.html", "/app.js",
      "/app.css", "/app**.js", "/views/**", "/models/**", "/assets/**", "/components/**",
      "/dist/**", "/logout", "/login.html", "/views/app/login/login.html"};
  public static final String   REST_PATH_PREFIX     = "/rest";

  public static final String ADMIN_PATH      = "/admin";
  public static final String INSTRUCTOR_PATH = "/instructor";
  public static final String STUDENT_PATH    = "/student";
  public static final String GRADER_PATH     = STUDENT_PATH + "/ta";

  public static final String LOGIN_PATH          = "/#/login";
  public static final String LOGIN_FAILURE_PATH  = LOGIN_PATH + "?error";
  public static final String LOGOUT_PATH         = "/logout";
  public static final String LOGOUT_SUCCESS_PATH = LOGIN_PATH + "?logout";

}
