package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.ADMIN_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.umdearborn.astronomyapp.controller.exception.ValidationErrorsException;
import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.service.UserManagementService;

@RestController
public class UserManagementController {

  private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

  private UserManagementService userManagementService;

  public UserManagementController(UserManagementService userManagementService) {
    this.userManagementService = userManagementService;
  }

  @RequestMapping(path = ADMIN_PATH + "/createuser", method = POST)
  public AstroAppUser createUser(@Valid @RequestBody AstroAppUser newUser, Errors errors) {
    if (errors.hasErrors()) {
      throw new ValidationErrorsException(errors);
    }
    logger.debug("Persisting new user {}", newUser.getEmail());
    return userManagementService.persistNewUser(newUser);
  }

}
