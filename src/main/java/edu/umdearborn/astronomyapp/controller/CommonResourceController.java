package edu.umdearborn.astronomyapp.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.repository.CourseUserRepository;

@RestController
public class CommonResourceController {

  private CourseUserRepository courseUserRepository;

  public CommonResourceController(CourseUserRepository courseUserRepository) {
    this.courseUserRepository = courseUserRepository;
  }

  @RequestMapping("/course/current")
  public Set<Course> getCourses(Principal principal) {
    return courseUserRepository.getCurrentCourses(principal.getName());
  }
}
