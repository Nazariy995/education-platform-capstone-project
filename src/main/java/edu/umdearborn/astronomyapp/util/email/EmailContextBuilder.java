package edu.umdearborn.astronomyapp.util.email;

import java.util.Map;

public interface EmailContextBuilder {

  public static final String EMAIL_SUBJECT_KEY = "email.subject";

  public static final String TEMPLATE_NAME_KEY = "template.name";

  public static final String TO_EMAIL_KEY = "email.to";

  public static final String USER_FIRST_NAME_KEY = "user.firstname";

  public static final String USER_LAST_NAME_KEY = "user.lastname";

  public Map<String, String> buildContext();
}
