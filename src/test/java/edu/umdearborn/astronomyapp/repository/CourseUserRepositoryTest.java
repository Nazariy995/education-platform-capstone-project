package edu.umdearborn.astronomyapp.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.umdearborn.astronomyapp.entity.CourseUser;


@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CourseUserRepositoryTest {

  @Autowired
  private CourseUserRepository repository;

  @Test
  public void getCurrentCoursesTest() {
    assertThat("Did not get all open courses",
        repository.getCurrentCourses("adminstructor1@umich.edu"), hasSize(3));
    assertThat("Did not get all open courses",
        repository.getCurrentCourses("adminstructor2@umich.edu"), hasSize(1));
    assertThat("Did not get all open courses",
        repository.getCurrentCourses("instructor3@umich.edu"), hasSize(1));
    assertThat("Did not get all open courses", repository.getCurrentCourses("user1@umich.edu"),
        hasSize(2));
  }

  @Test
  public void isInCourseTest() {
    assertThat("Should have access", repository.isInCourse("user11@umich.edu", "course1B"));
    assertThat("Should not have access", !repository.isInCourse("user11@umich.edu", "course1A"));
  }

  @Test
  public void getClassListTest() {
    assertThat(repository.getClassList("course1A"), hasSize(7));
  }

  @Test
  public void getRoleTest() {
    assertThat(repository.getRole("course1A", "user1@umich.edu"),
        Matchers.equalTo(CourseUser.CourseRole.STUDENT));
  }
}
