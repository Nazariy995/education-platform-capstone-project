package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.service.AclService;

@RestController
@RequestMapping(REST_PATH_PREFIX + STUDENT_PATH)
public class ModuleLockController {

  private static final Logger logger = LoggerFactory.getLogger(ModuleLockController.class);

  private AclService acl;

  public ModuleLockController(AclService acl) {
    this.acl = acl;
  }

  @RequestMapping(value = "/course/{courseId}/module/{moduleId}/checkin", method = GET)
  public List<String> getCheckinStatus(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId,
      @RequestParam(name = "courseUserId") String courseUserId, HttpSession session,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));

    return null;
  }

}
