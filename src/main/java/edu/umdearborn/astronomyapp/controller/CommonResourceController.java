package edu.umdearborn.astronomyapp.controller;

import static edu.umdearborn.astronomyapp.util.constants.UrlConstants.REST_PATH_PREFIX;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.repository.UserRepository;

@RestController
@RequestMapping(REST_PATH_PREFIX)
public class CommonResourceController {

  private static final Logger logger = LoggerFactory.getLogger(CommonResourceController.class);

  private UserRepository userRepository;

  public CommonResourceController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @RequestMapping(value = "/self", method = GET)
  public AstroAppUser getSelf(Principal principal) {
    logger.debug("Getting self");
    return userRepository.findByEmail(principal.getName());
  }

  @PostConstruct
  public void postConstruct() {
    Assert.notNull(userRepository);
  }
}
