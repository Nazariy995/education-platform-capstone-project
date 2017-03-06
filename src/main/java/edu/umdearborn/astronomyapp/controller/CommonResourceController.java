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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.service.CommonResourceService;
import edu.umdearborn.astronomyapp.service.FileUploadService;
import edu.umdearborn.astronomyapp.util.HttpSessionUtil;

@RestController
@RequestMapping(REST_PATH_PREFIX)
public class CommonResourceController {

  private static final Logger logger = LoggerFactory.getLogger(CommonResourceController.class);

  private CommonResourceService commonResourceService;
  private FileUploadService     fileUploadService;

  public CommonResourceController(FileUploadService fileUploadService,
      CommonResourceService commonResourceService) {
    this.commonResourceService = commonResourceService;
    this.fileUploadService = fileUploadService;
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

  @RequestMapping(value = "/session-{status}")
  public ResponseEntity<Void> sessionError(@PathVariable("status") String sessionStatus) {
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

    headers.add("session", sessionStatus);

    return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
  }

}
