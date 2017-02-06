package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.entity.ModuleGroup;
import edu.umdearborn.astronomyapp.service.AclService;
import edu.umdearborn.astronomyapp.service.GroupService;

@RestController
@RequestMapping(REST_PATH_PREFIX)
@SessionAttributes("courseUser")
public class ModuleGroupController {

  private AclService   acl;
  private GroupService groupService;

  public ModuleGroupController(AclService acl, GroupService groupService) {
    this.acl = acl;
    this.groupService = groupService;
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group",
      method = POST)
  public ModuleGroup createGroup(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId,
      @ModelAttribute("courseUser") Map<String, CourseUser> courseUser, Principal principal) {
    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));

    return groupService.createGroup(courseUser.get(moduleId).getId(), moduleId);
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}",
      method = POST)
  public List<CourseUser> joinGroup(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestBody CourseUser courseUser, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceInGroup(courseUser.getId(), groupId);

    return groupService.joinGroup(courseUser.getId(), moduleId, groupId);
  }

}
