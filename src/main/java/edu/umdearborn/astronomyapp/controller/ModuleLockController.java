package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.umdearborn.astronomyapp.service.AclService;

@RestController
@RequestMapping(REST_PATH_PREFIX + STUDENT_PATH)
@SessionAttributes("checkin")
public class ModuleLockController {

  private static final Logger logger = LoggerFactory.getLogger(ModuleLockController.class);

  private AclService acl;

  public ModuleLockController(AclService acl) {
    this.acl = acl;
  }

  @ModelAttribute("checkin")
  public Map<String, List<String>> createCheckin() {
    return new HashMap<String, List<String>>();
  }

  @RequestMapping(value = "/course/{courseId}/module/{moduleId}/checkin", method = GET)
  public List<String> getCheckinStatus(@PathVariable("courseId") String courseId,
      @PathVariable("moduleId") String moduleId,
      @ModelAttribute("checkin") Map<String, List<String>> checkin, Principal principal) {

    acl.enforceCourse(principal.getName(), courseId);

    List<String> checkinStatus =
        Optional.ofNullable(checkin.get(courseId + "/" + moduleId)).orElse(new ArrayList<String>());

    if (!checkinStatus.contains(principal.getName().toLowerCase())) {
      checkinStatus.add(principal.getName().toLowerCase());
      logger.info("Creating new entry for {}/{} checkin", courseId, moduleId);
    }

    checkin.put(courseId + "/" + moduleId, checkinStatus);

    return checkinStatus;
  }
}
