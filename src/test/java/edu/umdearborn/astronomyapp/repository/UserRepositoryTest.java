package edu.umdearborn.astronomyapp.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Ignore
public class UserRepositoryTest extends RepositoryTestHelper {

  @Autowired
  private UserRepository repository;

  @Test
  public void persistAndSelectTest() {
    assertEntityLoaded(repository.findByEmail("instructor4@umich.edu"));
    assertThat(repository.findByEmail("will-not-show-up@email.com"), nullValue());

  }

}
