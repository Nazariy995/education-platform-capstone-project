package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.GRADER_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.INSTRUCTOR_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.service.AclService;
import edu.umdearborn.astronomyapp.service.CourseService;
import edu.umdearborn.astronomyapp.util.ValidAssert;
import edu.umdearborn.astronomyapp.util.json.JsonDecorator;

@RestController
@RequestMapping(REST_PATH_PREFIX)
public class CourseController {

  private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

  private AclService    acl;
  private CourseService courseService;

  public CourseController(AclService acl, CourseService courseService) {
    this.acl = acl;
    this.courseService = courseService;
  }

  @RequestMapping(value = {INSTRUCTOR_PATH + "/courses", STUDENT_PATH + "/courses"}, method = GET)
  public List<Course> getCurrentCourses(
      @RequestParam(name = "hideClosed", defaultValue = "true") boolean hideClosed,
      @RequestParam(name = "hideOpenSoon", defaultValue = "true") boolean hideOpenSoon,
      Principal principal) {

    return courseService.getCourses(principal.getName(), hideClosed, hideOpenSoon);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course", method = POST)
  public Course createCourse(@Valid @RequestBody Course course, Errors errors) {

    ValidAssert.isValid(errors);

    return courseService.createCourse(course);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}", method = PUT)
  public Course updateCourse(@Valid @RequestBody Course course, Errors errors,
      @PathVariable("courseId") String courseId,
      @RequestParam(name = "courseUserId") String courseUserId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);

    ValidAssert.isValid(errors);
    course.setId(courseId);
    return courseService.updateCourse(course);
  }

  @RequestMapping(value = {STUDENT_PATH + "/course/{courseId}/users",
      GRADER_PATH + "/course/{courseId}/users", INSTRUCTOR_PATH + "/course/{courseId}/users"},
      method = GET)
  public List<CourseUser> getClassList(@PathVariable("courseId") String courseId,
      @RequestParam(name = "roles", defaultValue = "") List<CourseUser.CourseRole> roles,
      @RequestParam(name = "courseUserId") String courseUserId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);

    if (roles == null || roles.isEmpty()) {
      logger.debug("Setting empty roles parameter");
      roles = Arrays.asList(CourseUser.CourseRole.INSTRUCTOR, CourseUser.CourseRole.TA,
          CourseUser.CourseRole.STUDENT);
    }

    return courseService.getClassList(courseId, roles);
  }

  @RequestMapping(value = {STUDENT_PATH + "/course/{courseId}", GRADER_PATH + "/course/{courseId}",
      INSTRUCTOR_PATH + "/course/{courseId}"}, method = GET)
  public JsonDecorator<Course> getCourseDetails(@PathVariable("courseId") String courseId,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);

    Course course = courseService.getCourseDetails(courseId);

    if (course != null) {
      CourseUser courseUser = courseService.getCourseUser(principal.getName(), courseId);

      if (courseUser != null) {
        JsonDecorator<Course> decorator = new JsonDecorator<>();
        decorator.setPayload(course);
        decorator.addProperty("courseUserId", courseUser.getId());
        decorator.addProperty("courseRole", courseUser.getRole());

        return decorator;
      }

      logger.info("Cannot find details for user: '{}' in course: '{}'", principal.getName(),
          courseId);
    }

    logger.info("Course: '{}' does not exist", courseId);
    return null;


  }

}
