package edu.umdearborn.astronomyapp.util.email;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.util.Assert;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;

public class NewUserEmailContextBuilder implements EmailContextBuilder {

  public static final String EMAIL_SUBJECT = "Welcome to UMD Astronomy Learning Module Application";

  public static final String TEMPLATE = "/email/new-user-email-template.htm";

  private AstroAppUser user;

  public NewUserEmailContextBuilder(AstroAppUser user) {
    this.user = user;
  }

  @Override
  public Map<String, String> buildContext() {
    Map<String, String> context = new HashMap<>();
    context.put(TEMPLATE_NAME_KEY, TEMPLATE);
    context.put(TO_EMAIL_KEY, user.getEmail());
    context.put(EMAIL_SUBJECT_KEY, EMAIL_SUBJECT);
    context.put(USER_FIRST_NAME_KEY, user.getFirstName());
    context.put(USER_LAST_NAME_KEY, user.getLastName());
    context.put("user.temppass", user.getPassword());
    return context;
  }

  @PostConstruct
  public void postConstruct() {
    Assert.notNull(user);
  }

}
