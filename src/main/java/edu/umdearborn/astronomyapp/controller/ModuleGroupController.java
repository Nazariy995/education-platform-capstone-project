package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.INSTRUCTOR_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import edu.umdearborn.astronomyapp.service.AutoGradeService;
import edu.umdearborn.astronomyapp.service.GradeService;
import edu.umdearborn.astronomyapp.service.GroupService;
import edu.umdearborn.astronomyapp.util.HttpSessionUtil;
import edu.umdearborn.astronomyapp.util.json.JsonDecorator;

@RestController
@RequestMapping(REST_PATH_PREFIX)
@SessionAttributes("courseUser")
public class ModuleGroupController {

  private static final Logger logger = LoggerFactory.getLogger(ModuleGroupController.class);

  private AclService       acl;
  private GroupService     groupService;
  private GradeService     gradeService;
  private AutoGradeService autoGradeService;

  public ModuleGroupController(AclService acl, GroupService groupService, GradeService gradeService,
      AutoGradeService autoGradeService) {
    this.acl = acl;
    this.groupService = groupService;
    this.gradeService = gradeService;
    this.autoGradeService = autoGradeService;
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group",
      method = POST)
  public JsonDecorator<ModuleGroup> createGroup(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, HttpSession session, Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceModuleOpen(moduleId);
    acl.enforeModuleNotClosed(moduleId);

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
      HttpSession session, @PathVariable("removeUser") String removedUser, Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceInGroup(courseUserId, groupId);
    acl.enforeModuleNotClosed(moduleId);

    return groupService.removeFromGroup(groupId, removedUser);
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/free", method = GET)
  public List<CourseUser> getFreeAgents(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, HttpSession session, Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));

    Optional<List<CourseUser>> optional =
        Optional.ofNullable(groupService.getFreeUsers(courseId, moduleId));

    return optional.orElse(new ArrayList<CourseUser>());
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group", method = GET)
  public JsonDecorator<ModuleGroup> getGroup(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, HttpSession session, Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

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
      boolean hasLock = groupService.hasLock(g.getId(),
          getCheckinSessionAttribute(session, g.getId(), courseUserId));
      json.addProperty("hasLock", hasLock);
      json.addProperty("isModuleEditable", hasLock && true);
    });

    return json;

  }

  @RequestMapping(
      value = STUDENT_PATH
          + "/course/{courseId}/module/{moduleId}/group/{groupId}/member/{addMember}",
      method = POST)
  public List<CourseUser> joinGroup(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      HttpSession session, @PathVariable("addMember") String addMember, Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceInGroup(courseUserId, groupId);
    acl.enforceGroupLocked(groupId, false);
    acl.enforeModuleNotClosed(moduleId);
    acl.enforceModuleOpen(moduleId);

    return groupService.joinGroup(addMember, moduleId, groupId);
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/checkin",
      method = GET)
  public JsonDecorator<List<String>> getCheckinStatus(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      HttpSession session, Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);

    List<String> checkin = getCheckinSessionAttribute(session, groupId, courseUserId);

    JsonDecorator<List<String>> json = new JsonDecorator<>();
    json.setPayload(checkin);
    boolean hasLock =
        groupService.hasLock(groupId, getCheckinSessionAttribute(session, groupId, courseUserId));
    json.addProperty("hasLock", hasLock);
    json.addProperty("isModuleEditable", hasLock && true);

    return json;
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/checkin-reset",
      method = POST)
  public JsonDecorator<List<String>> resetCheckin(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      HttpSession session, Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);

    List<String> checkin = new ArrayList<>();
    checkin.add(courseUserId);
    session.setAttribute(groupId, checkin);

    JsonDecorator<List<String>> json = new JsonDecorator<>();
    json.setPayload(checkin);
    boolean hasLock =
        groupService.hasLock(groupId, getCheckinSessionAttribute(session, groupId, courseUserId));
    json.addProperty("hasLock", hasLock);
    json.addProperty("isModuleEditable", hasLock && true);

    return json;
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/checkin",
      method = POST)
  public JsonDecorator<List<String>> checkin(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestBody Map<String, String> checkinUser, HttpSession session, Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceModuleOpen(moduleId);
    acl.enforeModuleNotClosed(moduleId);
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);
    acl.enforceGroupLocked(groupId, true);

    List<String> checkin = getCheckinSessionAttribute(session, groupId, courseUserId);

    logger.debug("Current checkin status: {}", Arrays.toString(checkin.toArray()));

    Optional<CourseUser> optional = Optional
        .ofNullable(groupService.checkin(Optional.ofNullable(checkinUser.get("email")).orElse(""),
            Optional.ofNullable(checkinUser.get("password")).orElse(""), groupId));

    CourseUser user = optional.orElseThrow(
        () -> new AccessDeniedException("User: " + checkinUser.get("email") + " cannot checkin"));

    if (!checkin.contains(user.getId())) {
      checkin.add(user.getId());
      session.setAttribute(groupId, checkin);
      logger.debug("After checkin status: {}", Arrays.toString(checkin.toArray()));
    }

    JsonDecorator<List<String>> json = new JsonDecorator<>();
    boolean hasLock =
        groupService.hasLock(groupId, getCheckinSessionAttribute(session, groupId, courseUserId));
    json.addProperty("hasLock", hasLock);
    json.addProperty("isModuleEditable", hasLock && true);

    return json;
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/members",
      method = GET)
  public List<CourseUser> getGroupRoster(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      HttpSession session, Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);

    return groupService.getUsersInGroup(groupId);
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/canEdit",
      method = GET)
  public Map<String, Boolean> hasLock(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "page", defaultValue = "1") int pageNumber, HttpSession session,
      Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);

    List<String> checkedIn = getCheckinSessionAttribute(session, groupId, courseUserId);

    Map<String, Boolean> map = new HashMap<>();
    boolean hasLock = groupService.hasLock(groupId, checkedIn);
    map.put("hasLock", hasLock);
    map.put("isModuleEditable",
        hasLock && autoGradeService.answeredGatekeepers(moduleId, pageNumber, groupId));

    return map;
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/answers/save",
      method = POST)
  public Map<String, Answer> saveAnswers(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      HttpSession session, @RequestBody Map<String, Map<String, String>> answers,
      Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceModuleOpen(moduleId);
    acl.enforeModuleNotClosed(moduleId);
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);
    acl.enforceGroupLocked(groupId, true);
    acl.enforceHasLock(groupId, getCheckinSessionAttribute(session, groupId, courseUserId));


    return Optional.ofNullable(groupService.saveAnswers(answers, groupId))
        .orElse(new ArrayList<Answer>()).parallelStream()
        .collect(Collectors.toMap(a -> a.getQuestion().getId(), a -> a));

  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/answers/submit",
      method = POST)
  public List<Answer> submitAnswers(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      HttpSession session, Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceModuleOpen(moduleId);
    acl.enforeModuleNotClosed(moduleId);
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceInGroup(courseUserId, groupId);
    acl.enforceGroupLocked(groupId, true);
    acl.enforceHasLock(groupId, getCheckinSessionAttribute(session, groupId, courseUserId));

    return groupService.submitAnswers(groupId);
  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/answers",
      method = GET)
  public Map<String, Answer> getAnswers(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      @RequestParam(name = "showSaved", defaultValue = "true") boolean showSavedAnswers,
      Principal principal, HttpSession session) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceGroupLocked(groupId, true);
    acl.enforceInGroup(courseUserId, groupId);

    return Optional.ofNullable(groupService.getAnswers(groupId, showSavedAnswers))
        .orElse(new ArrayList<Answer>()).parallelStream()
        .collect(Collectors.toMap(a -> a.getQuestion().getId(), a -> a));

  }

//  @RequestMapping(
//      value = INSTRUCTOR_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/answers",
//      method = GET)
//  public Map<String, Answer> getSubmissions(@PathVariable("courseId") String courseId,
//      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
//      Principal principal, HttpSession session) {
//
//    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);
//
//    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
//
//    return Optional.ofNullable(groupService.getAnswers(groupId, false))
//        .orElse(new ArrayList<Answer>()).parallelStream()
//        .collect(Collectors.toMap(a -> a.getQuestion().getId(), a -> a));
//
//  }

  @RequestMapping(
      value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/finalize",
      method = POST)
  public ResponseEntity<Void> finalizeGroup(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      HttpSession session, Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforeModuleNotClosed(moduleId);
    acl.enforceModuleOpen(moduleId);
    acl.enforceInGroup(courseUserId, groupId);
    acl.enforceGroupLocked(groupId, false);

    groupService.finalizeGroup(groupId);

    return ResponseEntity.ok().build();
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/module/{moduleId}/groups",
      method = GET)
  public Map<String, List<CourseUser>> getGroups(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);

    return groupService.getGroups(moduleId);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}/answers",
      method = GET)
  public Map<String, Answer> getAnswers(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceModuleClosed(moduleId);

    Optional.ofNullable(groupService.getAnswers(groupId, false)).orElse(new ArrayList<Answer>())
        .stream().filter(a -> a.getQuestion().isMachineGradeable() && a.getPointesEarned() == null)
        .forEach(a -> {
          BigDecimal points;
          if (autoGradeService.checkAnswer(a.getId())) {
            logger.debug("Answer: '{}' with value '{}' for question: '{}' is not correct",
                a.getId(), a.getValue(), a.getQuestion().getId());
            points = a.getQuestion().getPoints();
          } else {
            logger.debug("Answer: '{}' with value '{}' for question: '{}' is correct", a.getId(),
                a.getValue(), a.getQuestion().getId());
            points = BigDecimal.ZERO;
          }

          autoGradeService.setPointsEarned(a.getId(), points);
        });

    return Optional.ofNullable(groupService.getAnswers(groupId, false))
        .orElse(new ArrayList<Answer>()).parallelStream()
        .collect(Collectors.toMap(a -> a.getQuestion().getId(), a -> a));
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/module/{moduleId}/group/{groupId}",
      method = POST)
  public Map<String, Answer> gradeAnswers(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("groupId") String groupId,
      Principal principal, @RequestBody Map<String, Map<String, String>> answers) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforceGroupInCourse(groupId, courseId);
    acl.enforceModuleClosed(moduleId);

    // return Optional.ofNullable(groupService.gradeAnswers(answers)).orElse(new
    // ArrayList<Answer>())
    // .parallelStream().collect(Collectors.toMap(a -> a.getQuestion().getId(), a -> a));

    groupService.gradeAnswers(answers);

    return Optional.ofNullable(groupService.getAnswers(groupId, false))
        .orElse(new ArrayList<Answer>()).parallelStream()
        .collect(Collectors.toMap(a -> a.getQuestion().getId(), a -> a));
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}/grade", method = GET)
  public Map<String, Object> getGrade(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforceIsCourseRole(principal.getName(), courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT));

    return gradeService.getGrade(principal.getName(), moduleId);
  }

  private List<String> getCheckinSessionAttribute(HttpSession session, String groupId,
      String courseUserId) {

    @SuppressWarnings("unchecked")
    List<String> checkin = HttpSessionUtil.getAttributeOrDefault(session, groupId, List.class,
        new ArrayList<String>());

    if (!checkin.contains(courseUserId)) {
      checkin.add(courseUserId);
      session.setAttribute(groupId, checkin);
    }

    return checkin;
  }

}
