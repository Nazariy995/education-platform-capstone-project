package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.umdearborn.astronomyapp.entity.Module;
import edu.umdearborn.astronomyapp.service.AclService;
import edu.umdearborn.astronomyapp.service.ModuleService;
import edu.umdearborn.astronomyapp.util.jsondecorator.JsonDecorator;

@RestController
@RequestMapping(REST_PATH_PREFIX)
public class ModuleController {

  private AclService    acl;
  private ModuleService moduleService;

  public ModuleController(AclService acl, ModuleService moduleService) {
    this.acl = acl;
    this.moduleService = moduleService;
  }

  @RequestMapping(value = STUDENT_PATH + "/course/{courseId}/module/{moduleId}", method = GET)
  public JsonDecorator<Module> getOpenModuleDetails(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId,
      @RequestParam(name = "courseUserId") String courseUserId, Principal principal) {

    acl.enforceInCourse(principal.getName(), courseId, courseUserId);
    acl.enforeceModuleInCourse(courseId, moduleId);
    acl.enforceModuleVisible(moduleId);

    return moduleService.getModuleDetails(moduleId);
  }
}
