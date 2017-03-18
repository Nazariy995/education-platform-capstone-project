package edu.umdearborn.astronomyapp.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Ignore;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.AstroAppUser.Role;

public class AstroAppUserValidationTest extends ValidationTestHelper {

	
  @Ignore
  public void validAppUsersTest() {
    AstroAppUser user = new AstroAppUser();
    user.setEmail("email@email.com");
    user.setFirstName("fname");
    user.setLastName("lname");
    user.setPassword("password");
    user.setRoles(new HashSet<Role>(Arrays.asList(Role.ADMIN, Role.INSTRUCTOR)));
    assertThat(super.validator.validate(user), empty());
    user.setRoles(new HashSet<Role>(Arrays.asList(Role.USER)));
    assertThat(super.validator.validate(user), empty());
  }
}
