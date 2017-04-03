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
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BeanPropertyBindingResult;
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
import com.google.gson.Gson;

import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.Module;
import edu.umdearborn.astronomyapp.entity.MultipleChoiceQuestion;
import edu.umdearborn.astronomyapp.entity.NumericQuestion;
import edu.umdearborn.astronomyapp.entity.PageItem;
import edu.umdearborn.astronomyapp.entity.PageItem.PageItemType;
import edu.umdearborn.astronomyapp.entity.Question;
import edu.umdearborn.astronomyapp.entity.Question.QuestionType;
import edu.umdearborn.astronomyapp.service.AclService;
import edu.umdearborn.astronomyapp.service.GradeService;
import edu.umdearborn.astronomyapp.service.ModuleService;
import edu.umdearborn.astronomyapp.util.HttpSessionUtil;
import edu.umdearborn.astronomyapp.util.ValidAssert;
import edu.umdearborn.astronomyapp.util.json.JsonDecorator;
import edu.umdearborn.astronomyapp.util.json.View;
import edu.umdearborn.astronomyapp.validation.validator.MultipleChoiceQuestionValidator;
import edu.umdearborn.astronomyapp.validation.validator.NumericQuestionValidator;
import edu.umdearborn.astronomyapp.validation.validator.PageItemValidator;
import edu.umdearborn.astronomyapp.validation.validator.QuestionValidator;

@RestController
@RequestMapping(REST_PATH_PREFIX)
public class ModuleController {

  private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

  private AclService    acl;
  private ModuleService moduleService;
  private ObjectMapper  objectMapper;
  private GradeService  gradeService;

  public ModuleController(AclService acl, ModuleService moduleService, ObjectMapper objectMapper,
      GradeService gradeService) {
    this.acl = acl;
    this.moduleService = moduleService;
    this.objectMapper = objectMapper;
    this.gradeService = gradeService;
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
    acl.enforeceCourseNotClosed(courseId);

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
  public List<Module> deleteModule(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, HttpSession session, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforeceModuleInCourse(courseId, moduleId);
    acl.enforceModuleNotOpen(moduleId);

    moduleService.deleteModule(moduleId);

    return moduleService.getModules(courseId, false);
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
  public JsonDecorator<Module> deleteModulePage(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId,
      @RequestParam(name = "page", defaultValue = "1") int pageNumber, HttpSession session,
      Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforeceModuleInCourse(courseId, moduleId);
    acl.enforceModuleNotOpen(moduleId);

    moduleService.deletePage(moduleId, pageNumber);

    return moduleService.getModuleDetails(moduleId);
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
    acl.enforceModuleNotOpen(moduleId);

    Errors errors;
    PageItem item;
    Gson gson = new Gson();
    if (PageItemType.TEXT.toString().equalsIgnoreCase(json.get("pageItemType").asText())) {
      logger.debug("Is a text item");
      item = objectMapper.treeToValue(json, PageItem.class);
      logger.debug("Value: {}", item);
      errors = new BeanPropertyBindingResult(item, "pageItem");
      new PageItemValidator().validate(item, errors);
    } else if (QuestionType.MULTIPLE_CHOICE.toString()
        .equalsIgnoreCase(json.get("questionType").asText())) {
      logger.debug("Is a multiple choice question");
      item = gson.fromJson(json.toString(), MultipleChoiceQuestion.class);
      item.setPageItemType(PageItem.PageItemType.QUESTION);
      logger.debug("Value: {}", item);
      errors = new BeanPropertyBindingResult(item, "multipleChoiceQuestion");
      new MultipleChoiceQuestionValidator().validate(item, errors);
    } else if (QuestionType.NUMERIC.toString()
        .equalsIgnoreCase(json.get("questionType").asText())) {
      logger.debug("Is a numeric question");
      item = gson.fromJson(json.toString(), NumericQuestion.class);
      item.setPageItemType(PageItem.PageItemType.QUESTION);
      logger.debug("Value: {}", item);
      errors = new BeanPropertyBindingResult(item, "numericQuestion");
      new NumericQuestionValidator().validate(item, errors);
    } else {
      logger.debug("Is a generic question");
      item = objectMapper.treeToValue(json, Question.class);
      item.setPageItemType(PageItem.PageItemType.QUESTION);
      logger.debug("Value: {}", item);
      errors = new BeanPropertyBindingResult(item, "question");
      new QuestionValidator().validate(item, errors);
    }

    ValidAssert.isValid(errors);

    logger.debug("Creating: {}", item.toString());

    return moduleService.createPageItem(item, moduleId, pageNumber);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/module/{moduleId}/item/{itemId}",
      method = DELETE)
  public List<PageItem> deletePageItem(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, @PathVariable("itemId") String itemId,
      Principal principal) {
    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforeceModuleInCourse(courseId, moduleId);
    acl.enforceModuleNotOpen(moduleId);

    return moduleService.deletePageItem(moduleId, itemId);
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/module/{moduleId}/grades",
      method = GET)
  public Map<String, Object> getGrades(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId, Principal principal) {
    acl.enforceInCourse(principal.getName(), courseId);
    acl.enforeceModuleInCourse(courseId, moduleId);

    return gradeService.getGrades(courseId, moduleId);
  }

}
