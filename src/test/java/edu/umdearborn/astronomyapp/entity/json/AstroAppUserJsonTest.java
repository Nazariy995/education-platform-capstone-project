package edu.umdearborn.astronomyapp.entity.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.json.JsonContent;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.AstroAppUser.Role;

public class AstroAppUserJsonTest extends JsonTestHelper<AstroAppUser> {
  
  private static final Logger logger = LoggerFactory.getLogger(AstroAppUserJsonTest.class);
  
  @Test
  public void jsonTest() {
    AstroAppUser user = new AstroAppUser();
    user.setEmail("test@email.com");
    user.setFirstName("Fname");
    user.setLastName("Lname");
    user.setPassword("dkljhfdkljhEKJHASjkhfdlah7qett");
    user.getRoles().add(Role.ADMIN);
    user.getRoles().add(Role.INSTRUCTOR);
    JsonContent<?> json = super.writeSafely(user);
    logger.info("Json string:\n{}", json.getJson());
    assertThat(json).doesNotHaveJsonPathValue("@.password");
    assertThat(json).doesNotHaveJsonPathValue("@.id");
  }

}
