package edu.umdearborn.astronomyapp.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AstroAppUserTest {

  private static final Logger logger = LoggerFactory.getLogger(AstroAppUserTest.class);

  @Test
  public void toStringTest() {
    AstroAppUser user = new AstroAppUser();
    user.setEmail("test@email.com");
    user.setFirstName("Fname");
    user.setLastName("Lname");
    assertThat(user.toString(), containsString("password=<null>"));
    logger.trace(user.toString());
    user.setPassword("dkljhfdkljhEKJHASjkhfdlah7qett");
    assertThat(user.toString(), containsString("password=******"));
    logger.trace(user.toString());
  }

  @Test
  public void equalsTest() {
    AstroAppUser user1 = new AstroAppUser();
    user1.setEmail("test@email.com");
    user1.setFirstName("Fname");
    user1.setLastName("Lname");
    user1.setPassword("dkljhfdkljhEKJHASjkhfdlah7qett");
    AstroAppUser user2 = new AstroAppUser();
    user2.setEmail("test@email.com");
    user2.setFirstName("Fname");
    user2.setLastName("Lname");
    user2.setPassword("different password");
    assertThat("Equals method is checking the password field", user1.equals(user2));
    user2.setEnabled(false);
    assertThat("user1.enabled does not equal user2.enabled", !user1.equals(user2));
  }

}
