package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.entity.ModuleGroup;
import edu.umdearborn.astronomyapp.service.AclService;
import edu.umdearborn.astronomyapp.service.GroupService;
import edu.umdearborn.astronomyapp.util.json.JsonDecorator;

@RestController
@RequestMapping(REST_PATH_PREFIX)
@SessionAttributes("courseUser")
public class ModuleGroupController {

  private static final Logger logger = LoggerFactory.getLogger(ModuleGroupController.class);

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
      @RequestParam(name = "courseUserId") String courseUserId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));

    return groupService.createGroup(courseUserId, moduleId);
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group", method = GET)
  public JsonDecorator<ModuleGroup> getGroup(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId,
      @RequestParam(name = "courseUserId") String courseUserId, HttpSession session,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));

    ModuleGroup group = groupService.getGroup(courseUserId, moduleId);

    if (group != null) {
      JsonDecorator<ModuleGroup> json = new JsonDecorator<>();
      json.setPayload(group);
      json.addProperty("members", groupService.getUsersInGroup(group.getId()));

      json.addProperty("isModuleEditable", groupService.hasLock(group.getId(),
          getCheckinSessionAttribute(session, group.getId(), courseUserId)));

      logger.debug("Returning group and members and if editable");
      return json;
    }

    logger.debug("Not in group");

    return null;

  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}",
      method = POST)
  public List<CourseUser> joinGroup(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "courseUserId") String courseUserId, @RequestBody CourseUser courseUser,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceInGroup(courseUser.getId(), groupId);

    return groupService.joinGroup(courseUser.getId(), moduleId, groupId);
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/checkin",
      method = GET)
  public JsonDecorator<List<String>> getCheckinStatus(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "courseUserId") String courseUserId, HttpSession session,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);

    List<String> checkin = getCheckinSessionAttribute(session, groupId, courseUserId);
    
    JsonDecorator<List<String>> json = new JsonDecorator<>();
    json.setPayload(checkin);
    json.addProperty("isModuleEditable", groupService.hasLock(groupId,
        getCheckinSessionAttribute(session, groupId, courseUserId)));

    return json;
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/checkin",
      method = POST)
  public JsonDecorator<List<String>> checkin(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "courseUserId") String courseUserId,
      @RequestBody Map<String, String> checkinUser, HttpSession session, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);

    List<String> checkin = getCheckinSessionAttribute(session, groupId, courseUserId);

    CourseUser user =
        groupService.checkin(checkinUser.get("email"), checkinUser.get("password"), groupId);

    if (user != null && !checkin.contains(user.getId())) {
      checkin.add(user.getId());
      session.setAttribute(groupId, checkin);
    }
    
    JsonDecorator<List<String>> json = new JsonDecorator<>();
    json.setPayload(checkin);
    json.addProperty("isModuleEditable", groupService.hasLock(groupId,
        getCheckinSessionAttribute(session, groupId, courseUserId)));

    return json;
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}",
      method = GET)
  public List<CourseUser> getGroupRoster(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "courseUserId") String courseUserId, HttpSession session,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);

    return groupService.getUsersInGroup(groupId);
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/lock",
      method = GET)
  public boolean hasLock(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "courseUserId") String courseUserId, HttpSession session,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);

    List<String> checkedIn = getCheckinSessionAttribute(session, groupId, courseUserId);

    return groupService.hasLock(groupId, checkedIn);
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/save",
      method = POST)
  public boolean saveAnswer(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "courseUserId") String courseUserId,
      @RequestBody Map<String, String> answers, HttpSession session, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);

    List<String> checkedIn = getCheckinSessionAttribute(session, groupId, courseUserId);

    return groupService.hasLock(groupId, checkedIn);
  }

  private List<String> getCheckinSessionAttribute(HttpSession session, String groupId,
      String courseUserId) {
    @SuppressWarnings("unchecked")
    List<String> checkin = (List<String>) session.getAttribute(groupId);

    if (checkin == null) {
      checkin = new ArrayList<String>();
      logger.debug("Creating checkin");
    }

    if (!checkin.contains(courseUserId)) {
      checkin.add(courseUserId);
      session.setAttribute(groupId, checkin);
    }

    return checkin;
  }

}
