package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;

import java.security.Principal;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.repository.CourseUserRepository;
import edu.umdearborn.astronomyapp.repository.UserRepository;

@RestController
@RequestMapping(REST_PATH_PREFIX)
public class CommonResourceController {

  private static final Logger logger = LoggerFactory.getLogger(CommonResourceController.class);
  
  private CourseUserRepository courseUserRepository;
  
  private UserRepository userRepository;

  public CommonResourceController(CourseUserRepository courseUserRepository, UserRepository userRepository) {
    this.courseUserRepository = courseUserRepository;
    this.userRepository = userRepository;
  }

  @RequestMapping("/course/current")
  public Set<Course> getCourses(Principal principal) {
    return courseUserRepository.getCurrentCourses(principal.getName());
  }
  
  @RequestMapping("/self")
  public AstroAppUser getSelf(Principal principal) {
    logger.debug("Getting self");
    return userRepository.findByEmail(principal.getName());
  }
  
  @PostConstruct
  public void postConstruct() {
    Assert.notNull(courseUserRepository);
    Assert.notNull(userRepository);
  }
}
