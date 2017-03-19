package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.INSTRUCTOR_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.Module;
import edu.umdearborn.astronomyapp.entity.MultipleChoiceQuestion;
import edu.umdearborn.astronomyapp.entity.NumericQuestion;
import edu.umdearborn.astronomyapp.entity.PageItem;
import edu.umdearborn.astronomyapp.entity.PageItem.PageItemType;
import edu.umdearborn.astronomyapp.entity.Question;
import edu.umdearborn.astronomyapp.entity.Question.QuestionType;
import edu.umdearborn.astronomyapp.service.AclService;
import edu.umdearborn.astronomyapp.service.ModuleService;
import edu.umdearborn.astronomyapp.util.HttpSessionUtil;
import edu.umdearborn.astronomyapp.util.json.JsonDecorator;
import edu.umdearborn.astronomyapp.util.json.View;

@RestController
@RequestMapping(REST_PATH_PREFIX)
public class ModuleController {

  private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

  private AclService    acl;
  private ModuleService moduleService;
  private ObjectMapper  objectMapper;

  public ModuleController(AclService acl, ModuleService moduleService, ObjectMapper objectMapper) {
    this.acl = acl;
    this.moduleService = moduleService;
    this.objectMapper = objectMapper;
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/modules", method = GET)
  public List<Module> getModules(@PathVariable("courseId") String courseId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);

    return moduleService.getModules(courseId, false);
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/modules", method = GET)
  public List<Module> getVisibleModules(@PathVariable("courseId") String courseId,
      HttpSession session, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);

    return moduleService.getModules(courseId, true);
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}", params = "!page",
      method = GET)
  public JsonDecorator<Module> getOpenModuleDetails(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, HttpSession session, Principal principal) {

    String courseUserId = HttpSessionUtil.getCourseUserId(session, courseId);

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforeceModuleInCourse(courseId, moduleId);
    acl.enforceModuleOpen(moduleId);

    return moduleService.getModuleDetails(moduleId);
  }

  @JsonView(View.Student.class)
  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}", params = "page",
      method = GET)
  public List<PageItem> getOpenModulePage(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId,
      @RequestParam(name = "page", defaultValue = "1") int pageNumber, HttpSession session,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforeceModuleInCourse(courseId, moduleId);
    acl.enforceModuleOpen(moduleId);

    return moduleService.getPage(moduleId, pageNumber);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/module", method = POST)
  public Module createModule(@PathVariable("courseId") String courseId,
      @Valid @RequestBody Module module, Errors errors, HttpSession session, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);

    Course course = new Course();
    course.setId(courseId);
    module.setCourse(course);

    return moduleService.createModule(module);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/module/{moduleId}", method = PUT)
  public Module updateModule(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @Valid @RequestBody Module module, Errors errors,
      HttpSession session, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforeceModuleInCourse(courseId, moduleId);
    acl.enforceModuleNotOpen(moduleId);

    Course course = new Course();
    course.setId(courseId);
    module.setCourse(course);
    module.setId(moduleId);

    return moduleService.updateModule(module);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/module/{moduleId}", method = DELETE)
  public ResponseEntity<?> deleteModule(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @Valid @RequestBody Module module, Errors errors,
      HttpSession session, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforeceModuleInCourse(courseId, moduleId);
    acl.enforceModuleNotOpen(moduleId);

    moduleService.deleteModule(moduleId);

    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/module/{moduleId}",
      params = "!page", method = GET)
  public JsonDecorator<Module> getModuleDetails(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, HttpSession session, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforeceModuleInCourse(courseId, moduleId);

    return moduleService.getModuleDetails(moduleId);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/module/{moduleId}", params = "page",
      method = GET)
  public List<PageItem> getModulePage(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId,
      @RequestParam(name = "page", defaultValue = "1") int pageNumber, HttpSession session,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforeceModuleInCourse(courseId, moduleId);

    return moduleService.getPage(moduleId, pageNumber);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/module/{moduleId}", params = "page",
      method = DELETE)
  public ResponseEntity<?> deleteModulePage(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId,
      @RequestParam(name = "page", defaultValue = "1") int pageNumber, HttpSession session,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforeceModuleInCourse(courseId, moduleId);
    acl.enforceModuleNotOpen(moduleId);

    moduleService.deletePage(moduleId, pageNumber);

    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/module/{moduleId}/add-page",
      method = POST)
  public int addPage(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, HttpSession session, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforeceModuleInCourse(courseId, moduleId);
    acl.enforceModuleNotOpen(moduleId);

    return moduleService.addPage(moduleId);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/module/{moduleId}", params = "page",
      method = POST)
  public PageItem createItem(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @RequestBody ObjectNode json,
      @RequestParam(name = "page", defaultValue = "1") int pageNumber, HttpSession session,
      Principal principal) throws JsonProcessingException {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforeceModuleInCourse(courseId, moduleId);

    PageItem item;
    if (PageItemType.TEXT.toString().equalsIgnoreCase(json.get("pageItemType").asText())) {
      item = objectMapper.treeToValue(json, PageItem.class);
    } else if (QuestionType.MULTIPLE_CHOICE.toString()
        .equalsIgnoreCase(json.get("questionType").asText())) {
      item = objectMapper.treeToValue(json, MultipleChoiceQuestion.class);
    } else if (QuestionType.NUMERIC.toString()
        .equalsIgnoreCase(json.get("questionType").asText())) {
      item = objectMapper.treeToValue(json, NumericQuestion.class);
    } else {
      item = objectMapper.treeToValue(json, Question.class);
    }

    logger.debug("Creating: {}", item.toString());

    return moduleService.createPageItem(item, moduleId, pageNumber);
  }
}
