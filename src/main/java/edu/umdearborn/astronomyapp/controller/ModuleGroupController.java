package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.umdearborn.astronomyapp.entity.Answer;
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
  public JsonDecorator<ModuleGroup> createGroup(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, HttpSession session,
      @RequestParam(name = "courseUserId") String courseUserId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));

    ModuleGroup group = groupService.createGroup(courseUserId, moduleId);

    JsonDecorator<ModuleGroup> json = new JsonDecorator<>();
    json.setPayload(group);
    json.addProperty("isModuleEditable", groupService.hasLock(group.getId(),
        getCheckinSessionAttribute(session, group.getId(), courseUserId)));

    return json;
  }

  @RequestMapping(
      value = STUDENT_PATH
          + "/course/{courseId}/module/{moduleId}/group/{groupId}/member/{removeUser}",
      method = DELETE)
  public List<CourseUser> removeUser(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "courseUserId") String courseUserId,
      @PathVariable("removeUser") String removedUser, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceInGroup(courseUserId, groupId);

    return groupService.removeFromGroup(groupId, removedUser);
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/free", method = GET)
  public List<CourseUser> getFreeAgents(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId,
      @RequestParam(name = "courseUserId") String courseUserId, HttpSession session,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));

    Optional<List<CourseUser>> optional =
        Optional.ofNullable(groupService.getFreeUsers(courseId, moduleId));

    return optional.orElse(new ArrayList<CourseUser>());
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group", method = GET)
  public JsonDecorator<ModuleGroup> getGroup(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId,
      @RequestParam(name = "courseUserId") String courseUserId, HttpSession session,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));

    Optional<ModuleGroup> optional =
        Optional.ofNullable(groupService.getGroup(courseUserId, moduleId));
    JsonDecorator<ModuleGroup> json = new JsonDecorator<>();
    optional.ifPresent(g -> {
      logger.debug("Returning group and members and if editable");
      json.setPayload(g);
      json.addProperty("members", groupService.getUsersInGroup(g.getId()));
      json.addProperty("isModuleEditable", groupService.hasLock(g.getId(),
          getCheckinSessionAttribute(session, g.getId(), courseUserId)));
    });

    return json;

  }

  @RequestMapping(
      value = STUDENT_PATH
          + "/course/{courseId}/module/{moduleId}/group/{groupId}/member/{addMember}",
      method = POST)
  public List<CourseUser> joinGroup(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "courseUserId") String courseUserId,
      @PathVariable("addMember") String addMember, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceInGroup(courseUserId, groupId);
    acl.enforceGroupLocked(groupId, false);

    return groupService.joinGroup(addMember, moduleId, groupId);
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
    json.addProperty("isModuleEditable",
        groupService.hasLock(groupId, getCheckinSessionAttribute(session, groupId, courseUserId)));

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
    acl.enforceGroupLocked(groupId, true);

    List<String> checkin = getCheckinSessionAttribute(session, groupId, courseUserId);

    Optional<CourseUser> optional = Optional.ofNullable(
        groupService.checkin(checkinUser.get("email"), checkinUser.get("password"), groupId));

    CourseUser user = optional.orElseThrow(
        () -> new AccessDeniedException("User: " + checkinUser.get("email") + "cannot checkin"));

    if (!checkin.contains(user.getId())) {
      checkin.add(user.getId());
      session.setAttribute(groupId, checkin);
    }

    JsonDecorator<List<String>> json = new JsonDecorator<>();
    json.setPayload(checkin);
    json.addProperty("isModuleEditable",
        groupService.hasLock(groupId, getCheckinSessionAttribute(session, groupId, courseUserId)));

    return json;
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/members",
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
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/answers/save",
      method = POST)
  public List<Answer> saveAnswers(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "courseUserId") String courseUserId,
      @RequestBody Map<String, String> answers, HttpSession session, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);
    acl.enforceGroupLocked(groupId, true);
    acl.enforceHasLock(groupId, getCheckinSessionAttribute(session, groupId, courseUserId));

    return groupService.saveAnswers(answers, groupId);
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/answers/submit",
      method = POST)
  public List<Answer> submitAnswers(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "courseUserId") String courseUserId,
      @RequestBody Map<String, String> answers, HttpSession session, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);
    acl.enforceGroupLocked(groupId, true);
    acl.enforceHasLock(groupId, getCheckinSessionAttribute(session, groupId, courseUserId));

    return groupService.submitAnswers(groupId);
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/answers",
      method = GET)
  public List<Answer> getAnswers(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "courseUserId") String courseUserId,
      @RequestParam(name = "showSaved", defaultValue = "true") boolean showSavedAnswers,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceGroupLocked(groupId, true);
    acl.enforceInGroup(courseUserId, groupId);

    return groupService.getAnswers(groupId, showSavedAnswers);
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/finalize",
      method = POST)
  public ResponseEntity<Void> finalizeGroup(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "courseUserId") String courseUserId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);
    acl.enforceGroupLocked(groupId, false);

    groupService.finalizeGroup(groupId);

    return ResponseEntity.ok().build();
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
