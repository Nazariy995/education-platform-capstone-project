package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.ADMIN_PATH;
import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.service.UserManagementService;
import edu.umdearborn.astronomyapp.util.ValidAssert;;

//@RestController
//@RequestMapping(REST_PATH_PREFIX)
public class UserManagementController {

  private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

  private UserManagementService userManagementService;

  public UserManagementController(UserManagementService userManagementService) {
    this.userManagementService = userManagementService;
  }

  @RequestMapping(value = ADMIN_PATH + "/user", method = POST)
  public AstroAppUser createUser(@Valid @RequestBody AstroAppUser user, Errors errors) {
    ValidAssert.isValid(errors);
    logger.debug("Persisting new user {}", user.getEmail());
    return userManagementService.persistNewUser(user);
  }

  @RequestMapping(value = ADMIN_PATH + "/users", method = GET)
  public List<AstroAppUser> getUsers() {
    return null;
  }

  @RequestMapping(value = ADMIN_PATH + "/user/{email}", method = PUT)
  public AstroAppUser updateUser(@Valid @RequestBody AstroAppUser user, Errors errors,
      @PathVariable("email") String email) {
    ValidAssert.isValid(errors);
    return null;
  }

}
