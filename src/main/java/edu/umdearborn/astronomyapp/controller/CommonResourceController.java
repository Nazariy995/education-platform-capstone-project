package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.INSTRUCTOR_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.STUDENT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.service.CommonResourceService;
import edu.umdearborn.astronomyapp.service.FileUploadService;
import edu.umdearborn.astronomyapp.service.UserManagementService;
import edu.umdearborn.astronomyapp.util.HttpSessionUtil;

@RestController
@RequestMapping(REST_PATH_PREFIX)
public class CommonResourceController {

  private static final Logger logger = LoggerFactory.getLogger(CommonResourceController.class);

  private CommonResourceService commonResourceService;
  private FileUploadService     fileUploadService;
  private UserManagementService userManagementService;

  public CommonResourceController(FileUploadService fileUploadService,
      CommonResourceService commonResourceService, UserManagementService userManagementService) {
    this.commonResourceService = commonResourceService;
    this.fileUploadService = fileUploadService;
    this.userManagementService = userManagementService;
  }

  @RequestMapping(value = "/self", method = GET)
  public AstroAppUser getSelf(Principal principal, HttpSession session) {
    logger.debug("Getting self");
    AstroAppUser user = commonResourceService.findByEmail(principal.getName());
    Map<String, String> courseUserSummary =
        commonResourceService.getCourseUserSummary(principal.getName());

    logger.debug("Storing values into session");
    if (courseUserSummary != null && !courseUserSummary.isEmpty()) {
      HttpSessionUtil.putCourseUsers(session, courseUserSummary);
    }
    return user;
  }

  @RequestMapping(value = {INSTRUCTOR_PATH + "/upload", STUDENT_PATH + "/upload"}, method = POST)
  public String upload(@RequestParam("file") MultipartFile file)
      throws IOException, MimeTypeParseException {

    Assert.isTrue(!file.isEmpty(), "File must not be empty");
    Assert.isTrue("image".equalsIgnoreCase(new MimeType(file.getContentType()).getPrimaryType()),
        "File must be an image");

    logger.debug("Starting file upload to AWS for file '{}'", file.getOriginalFilename());
    return fileUploadService.upload(file.getInputStream(), file.getSize(), file.getContentType());
  }

  @RequestMapping(value = "/self/password/reset", method = POST)
  public ResponseEntity<Void> resetPassword(@RequestBody Map<String, String> payload) {
    if (payload.containsKey("email") && userManagementService.emailExists(payload.get("email"))) {
      logger.info("User: '{}' is resetting password", payload.get("email"));
      userManagementService.resetPassword(payload.get("email"));
    }

    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  @RequestMapping(value = "/self/password/change", method = POST)
  public ResponseEntity<Void> changePassword(@RequestBody Map<String, String> payload,
      Principal principal) {
    if (payload.containsKey("password") && payload.containsKey("confirm")
        && StringUtils.equals(payload.get("password"), payload.get("confirm"))
        && payload.get("password").length() > 5) {
      userManagementService.changePassword(principal.getName(), payload.get("password"));
      return new ResponseEntity<Void>(HttpStatus.OK);
    } else {
      logger.debug("Password is either not the same or not long engouh");
      return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
  }

}
