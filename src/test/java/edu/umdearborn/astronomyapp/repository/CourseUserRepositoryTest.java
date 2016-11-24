package edu.umdearborn.astronomyapp.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseUserRepositoryTest extends RepositoryTestHelper {

  @Autowired
  private CourseUserRepository repository;

  @Test
  public void getCurrentCoursesTest() {
    assertThat("Did not get all open courses", repository.getCurrentCourses("user1@email.com"),
        hasSize(2));
  }
}
