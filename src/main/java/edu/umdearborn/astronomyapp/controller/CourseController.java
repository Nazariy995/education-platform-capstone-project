package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.GRADER_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.INSTRUCTOR_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.entity.CourseUser.CourseRole;
import edu.umdearborn.astronomyapp.service.AclService;
import edu.umdearborn.astronomyapp.service.CourseService;
import edu.umdearborn.astronomyapp.service.CsvParserService;
import edu.umdearborn.astronomyapp.service.UserManagementService;
import edu.umdearborn.astronomyapp.util.HttpSessionUtil;
import edu.umdearborn.astronomyapp.util.ValidAssert;
import edu.umdearborn.astronomyapp.util.json.JsonDecorator;

@RestController
@RequestMapping(REST_PATH_PREFIX)
public class CourseController {

  private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

  private AclService            acl;
  private CourseService         courseService;
  private UserManagementService userManagementService;
  private CsvParserService      csvParserService;

  public CourseController(AclService acl, CourseService courseService,
      UserManagementService userManagementService, CsvParserService csvParserService) {
    this.acl = acl;
    this.courseService = courseService;
    this.userManagementService = userManagementService;
    this.csvParserService = csvParserService;
  }

  @RequestMapping(value = {INSTRUCTOR_PATH + "/courses", STUDENT_PATH + "/courses"}, method = GET)
  public List<Course> getCurrentCourses(
      @RequestParam(name = "hideClosed", defaultValue = "true") boolean hideClosed,
      @RequestParam(name = "hideOpenSoon", defaultValue = "true") boolean hideOpenSoon,
      Principal principal) {

    return courseService.getCourses(principal.getName(), hideClosed, hideOpenSoon);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course", method = POST, params = "!clone")
  public Course createCourse(@Valid @RequestBody Course course, Errors errors, Principal principal,
      HttpSession session) {

    ValidAssert.isValid(errors);

    course = courseService.createCourse(course, principal.getName());
    CourseUser courseUser = courseService.getCourseUser(principal.getName(), course.getId());
    Map<String, String> map = HttpSessionUtil.getCourseUsers(session);
    map.put(course.getId(), courseUser.getId());
    HttpSessionUtil.putCourseUsers(session, map);

    return course;
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course", method = POST)
  public Course cloneCourse(@Valid @RequestBody Course course,
      @RequestParam("clone") String cloneFromId, Errors errors, Principal principal,
      HttpSession session) {

    ValidAssert.isValid(errors);

    course = courseService.clone(course, cloneFromId, principal.getName());

    course = courseService.createCourse(course, principal.getName());
    CourseUser courseUser = courseService.getCourseUser(principal.getName(), course.getId());
    Map<String, String> map = HttpSessionUtil.getCourseUsers(session);
    map.put(course.getId(), courseUser.getId());
    HttpSessionUtil.putCourseUsers(session, map);

    return course;
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
      HttpSession session, Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

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

  @RequestMapping(value = INSTRUCTOR_PATH + "/courses/all", method = GET)
  public List<Course> getCourses() {
    return courseService.getCourses();
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/bulk-students", method = POST)
  public List<CourseUser> addStudentsToCourse(@PathVariable("courseId") String courseId,
      @RequestParam("file") MultipartFile file, Principal principal) {
    MimeType fileType = MimeType.valueOf(file.getContentType());
    logger.debug("Recieved filetype: '{}'", fileType);
    Assert.isTrue(
        MimeType.valueOf("text/csv").isCompatibleWith(fileType)
            || MimeType.valueOf("application/vnd.ms-excel").isCompatibleWith(fileType),
        "File must be a CSV");

    acl.enforceInCourse(principal.getName(), courseId);

    try {
      List<String[]> parsed = csvParserService.parseClassList(file.getInputStream(), fileType);
      if (parsed.isEmpty()) {
        logger.info("No valid results. Not changing anything");

        return courseService.getClassList(courseId, Arrays.asList(CourseUser.CourseRole.INSTRUCTOR,
            CourseUser.CourseRole.TA, CourseUser.CourseRole.STUDENT));
      }

      logger.debug("Parsed {} records correctly", parsed.size());

      CourseUser[] users = parsed.parallelStream().map(e -> {
        AstroAppUser u = new AstroAppUser();
        u.setEmail(e[0]);
        u.setFirstName(e[1]);
        u.setLastName(e[2]);
        u.setRoles(new HashSet<AstroAppUser.Role>(Arrays.asList(AstroAppUser.Role.USER)));
        CourseUser cu = new CourseUser();
        cu.setUser(u);
        cu.setRole(CourseRole.STUDENT);
        return cu;
      }).toArray(CourseUser[]::new);

      userManagementService.addUsersToCourse(courseId, users);
      return courseService.getClassList(courseId, Arrays.asList(CourseUser.CourseRole.INSTRUCTOR,
          CourseUser.CourseRole.TA, CourseUser.CourseRole.STUDENT));
    } catch (IOException ioe) {
      logger.error("Error parsing CSV", ioe);
      throw new RuntimeException(ioe);
    }

  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/student/{courseUserId}/drop",
      method = PUT)
  public List<CourseUser> drop(@PathVariable("courseId") String courseId,
      @PathVariable("courseUserId") String courseUserId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforceHasRoleInCourse(courseUserId, courseId,
        Arrays.asList(CourseUser.CourseRole.STUDENT, CourseUser.CourseRole.TA));

    userManagementService.updateCourseUserStatus(courseUserId, false);
    
    return courseService.getClassList(courseId, Arrays.asList(CourseUser.CourseRole.INSTRUCTOR,
        CourseUser.CourseRole.TA, CourseUser.CourseRole.STUDENT));
  }

}
