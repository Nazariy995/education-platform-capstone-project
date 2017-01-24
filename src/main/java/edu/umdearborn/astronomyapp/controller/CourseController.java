package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.INSTRUCTOR_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.security.Principal;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.repository.CourseRepository;
import edu.umdearborn.astronomyapp.repository.CourseUserRepository;
import edu.umdearborn.astronomyapp.service.AclService;
import edu.umdearborn.astronomyapp.util.ValidAssert;

@RestController
@RequestMapping(REST_PATH_PREFIX)
public class CourseController {

  private AclService acl;

  private CourseRepository courseRepository;

  private CourseUserRepository courseUserRepository;

  public CourseController(AclService acl, CourseRepository courseRepository,
      CourseUserRepository courseUserRepository) {
    this.acl = acl;
    this.courseRepository = courseRepository;
    this.courseUserRepository = courseUserRepository;
  }

  @RequestMapping(value = {INSTRUCTOR_PATH + "/courses/current", STUDENT_PATH + "/courses/current"},
      method = GET)
  public Set<Course> getCurrentCourses(Principal principal) {
    return courseUserRepository.getCurrentCourses(principal.getName());
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course", method = POST)
  public Course createCourse(@Valid @RequestBody Course course, Errors errors) {
    ValidAssert.isValid(errors);
    return courseRepository.saveAndFlush(course);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}", method = PUT)
  public Course updateCourse(@Valid @RequestBody Course course, Errors errors,
      @PathVariable("courseId") String courseId, Principal principal) {
    acl.enforceCourse(principal.getName(), courseId);
    ValidAssert.isValid(errors);
    course.setId(courseId);
    return courseRepository.saveAndFlush(course);
  }

}
