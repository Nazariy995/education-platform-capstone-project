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
    assertThat("Did not get all open courses",
        repository.getCurrentCourses("adminstructor1@gmail.com"), hasSize(3));
    assertThat("Did not get all open courses",
        repository.getCurrentCourses("adminstructor2@gmail.com"), hasSize(1));
    assertThat("Did not get all open courses",
        repository.getCurrentCourses("instructor3@gmail.com"), hasSize(1));
    assertThat("Did not get all open courses",
        repository.getCurrentCourses("user1@gmail.com"), hasSize(2));
  }
  
  @Test
  public void isInCourseTest() {
    assertThat("Should have access", repository.isInCourse("user11@gmail.com", "course1B"));
    assertThat("Should not have access", !repository.isInCourse("user11@gmail.com", "course1A"));
  }
}
