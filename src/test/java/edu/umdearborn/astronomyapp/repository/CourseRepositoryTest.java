package edu.umdearborn.astronomyapp.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Ignore
public class CourseRepositoryTest extends RepositoryTestHelper {

  @Autowired
  private CourseRepository repository;

  @Test
  public void getModuleTest() {
    assertThat("Did not get all visible modules", repository.getVisibleModules("course1A"), hasSize(2));
    assertThat("Did not get all visible modules", repository.getAllModules("course1A"), hasSize(3));
    assertThat("Did not get all visible modules", repository.getAllModules("no real course"), hasSize(0));
  }
}
