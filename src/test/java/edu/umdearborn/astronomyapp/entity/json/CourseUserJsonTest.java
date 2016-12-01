package edu.umdearborn.astronomyapp.entity.json;

import java.util.Calendar;
import java.util.UUID;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.json.JsonContent;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.AstroAppUser.Role;
import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;

public class CourseUserJsonTest extends JsonTestHelper<CourseUser> {

  private static final Logger logger = LoggerFactory.getLogger(CourseUserJsonTest.class);

  @Test
  public void testJson() {
    AstroAppUser user = new AstroAppUser();
    user.setId(UUID.randomUUID().toString());
    user.setEmail("test@email.com");
    user.setFirstName("Fname");
    user.setLastName("Lname");
    user.setPassword("dkljhfdkljhEKJHASjkhfdlah7qett");
    user.getRoles().add(Role.USER);

    Course course = new Course();
    course.setId(UUID.randomUUID().toString());
    course.setSubjectCode("CIS");
    course.setCourseCode("4951");
    course.setCourseTitle("Senior Design I");
    Calendar open = Calendar.getInstance();
    open.set(2016, 9, 7, 0, 0, 0);
    course.setOpenTimestamp(open.getTime());
    Calendar close = Calendar.getInstance();
    close.set(2016, 12, 13, 0, 0, 0);
    course.setCloseTimestamp(close.getTime());

    CourseUser courseUser = new CourseUser();
    courseUser.setId(UUID.randomUUID().toString());
    courseUser.setRole(CourseUser.CourseRole.STUDENT);
    courseUser.setUser(user);
    courseUser.setCourse(course);
    JsonContent<?> json = super.writeSafely(courseUser);
    logger.info("Json string:\n{}", json.getJson());
  }
}
