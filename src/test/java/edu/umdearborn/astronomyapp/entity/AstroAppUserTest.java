package edu.umdearborn.astronomyapp.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AstroAppUserTest {

  private static final Logger logger = LoggerFactory.getLogger(AstroAppUserTest.class);

  @Test
  public void toStringTest() {
    AstroAppUser user = new AstroAppUser();
    user.setEmail("test@email.com");
    user.setFirstName("Fname");
    user.setLastName("Lname");
    assertThat(user.toString(), containsString("passwordHash=<null>"));
    logger.trace(user.toString());
    user.setPasswordHash("dkljhfdkljhEKJHASjkhfdlah7qett");
    assertThat(user.toString(), containsString("passwordHash=******"));
    logger.trace(user.toString());
  }

  @Test
  public void equalsTest() {
    AstroAppUser user1 = new AstroAppUser();
    user1.setEmail("test@email.com");
    user1.setFirstName("Fname");
    user1.setLastName("Lname");
    user1.setPasswordHash("dkljhfdkljhEKJHASjkhfdlah7qett");
    AstroAppUser user2 = new AstroAppUser();
    user2.setEmail("test@email.com");
    user2.setFirstName("Fname");
    user2.setLastName("Lname");
    user2.setPasswordHash("different password");
    assertThat("Equals method is checking the password field", user1.equals(user2));
    user2.setEnabled(false);
    assertThat("user1.enabled does not equal user2.enabled", !user1.equals(user2));
  }
  
  @Test
  public void a() {
    int max = 0;
    for (int i = 0; i < 50; i++) {
      max = Math.max(max, new BCryptPasswordEncoder().encode("p").length());
    }
    
    System.out.println(max);
  }

}