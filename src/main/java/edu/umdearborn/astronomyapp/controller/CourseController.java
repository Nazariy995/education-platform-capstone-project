package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.GRADER_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.INSTRUCTOR_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.entity.Module;
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

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/modules", method = GET)
  public List<Module> getModules(@PathVariable("courseId") String courseId, Principal principal) {
    acl.enforceCourse(principal.getName(), courseId);
    return courseRepository.getAllModules(courseId);
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/modules", method = GET)
  public List<Module> getVisibleModules(@PathVariable("courseId") String courseId,
      Principal principal) {
    acl.enforceCourse(principal.getName(), courseId);
    return courseRepository.getVisibleModules(courseId);
  }

  @RequestMapping(
      value = {STUDENT_PATH + "/course/{courseId}/role", STUDENT_PATH + "/course/{courseId}/role",
          GRADER_PATH + "/course/{courseId}/role"},
      method = GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public CourseUser getCourseRole(@PathVariable("courseId") String courseId,
      Principal principal) {
    acl.enforceCourse(principal.getName(), courseId);
    CourseUser cu = new CourseUser();
    cu.setRole(courseUserRepository.getRole(courseId, principal.getName()));
    return cu;
  }

}
