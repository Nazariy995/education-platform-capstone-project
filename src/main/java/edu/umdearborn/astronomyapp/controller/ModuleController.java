package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.INSTRUCTOR_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import edu.umdearborn.astronomyapp.entity.Module;
import edu.umdearborn.astronomyapp.entity.PageItem;
import edu.umdearborn.astronomyapp.service.AclService;
import edu.umdearborn.astronomyapp.service.ModuleService;
import edu.umdearborn.astronomyapp.util.json.JsonDecorator;
import edu.umdearborn.astronomyapp.util.json.View;

@RestController
@RequestMapping(REST_PATH_PREFIX)
public class ModuleController {

  private AclService    acl;
  private ModuleService moduleService;

  public ModuleController(AclService acl, ModuleService moduleService) {
    this.acl = acl;
    this.moduleService = moduleService;
  }

  @RequestMapping(value = INSTRUCTOR_PATH + "/course/{courseId}/modules", method = GET)
  public List<Module> getModules(@PathVariable("courseId") String courseId,
      @RequestParam(name = "courseUserId") String courseUserId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);

    return moduleService.getModules(courseId, false);
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/modules", method = GET)
  public List<Module> getVisibleModules(@PathVariable("courseId") String courseId,
      @RequestParam(name = "courseUserId") String courseUserId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);

    return moduleService.getModules(courseId, true);
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}", params = "!page",
      method = GET)
  public JsonDecorator<Module> getOpenModuleDetails(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId,
      @RequestParam(name = "courseUserId") String courseUserId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforeceModuleInCourse(courseId, moduleId);
    acl.enforceModuleVisible(moduleId);

    return moduleService.getModuleDetails(moduleId);
  }

  @JsonView(View.Student.class)
  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}", params = "page",
      method = GET)
  public List<PageItem> getModulePage(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId,
      @RequestParam(name = "page", defaultValue = "1") int pageNumber,
      @RequestParam(name = "courseUserId") String courseUserId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforeceModuleInCourse(courseId, moduleId);
    acl.enforceModuleVisible(moduleId);

    return moduleService.getPage(moduleId, pageNumber);
  }
}
