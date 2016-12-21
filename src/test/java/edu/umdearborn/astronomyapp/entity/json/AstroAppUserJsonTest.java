package edu.umdearborn.astronomyapp.entity.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.io.IOException;
import java.util.UUID;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.AstroAppUser.Role;

public class AstroAppUserJsonTest extends JsonTestHelper<AstroAppUser> {

  private static final Logger logger = LoggerFactory.getLogger(AstroAppUserJsonTest.class);

  private JacksonTester<AstroAppUser> json;

  @Before
  public void before() {
    JacksonTester.initFields(this, new ObjectMapper());
  }

  @Test
  public void jsonTest() {
    AstroAppUser user = new AstroAppUser();
    user.setId(UUID.randomUUID().toString());
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

  @Test
  public void deseralizeTest() throws IOException {
    // @formatter:off
    AstroAppUser user = json.parseObject("{\r\n" + 
        "    \"email\": \"pbremer@umich.edu\",\r\n" + 
        "    \"firstName\": \"Patrick\",\r\n" + 
        "    \"lastName\": \"Bremer\",\r\n" + 
        "    \"appRoles\": [\"ADMIN\", \"INSTRUCTOR\"]\r\n" + 
        "}");
    // @formatter:on
    MatcherAssert.assertThat(user, notNullValue());
    MatcherAssert.assertThat(user.getEmail(), notNullValue());
    MatcherAssert.assertThat(user.getFirstName(), notNullValue());
    MatcherAssert.assertThat(user.getLastName(), notNullValue());
  }

}
