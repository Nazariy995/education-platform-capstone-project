package edu.umdearborn.astronomyapp.entity.json;

import java.util.Calendar;
import java.util.UUID;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.json.JsonContent;

import edu.umdearborn.astronomyapp.entity.Course;

public class CourseJsonTest extends JsonTestHelper<Course> {
  
  private static final Logger logger = LoggerFactory.getLogger(CourseJsonTest.class);
  
  @Test
  public void jsonTest() {
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
    JsonContent<?> json = super.writeSafely(course);
    logger.info("Json string:\n{}", json.getJson());
  }
}
