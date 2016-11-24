package edu.umdearborn.astronomyapp.controller;

import java.security.Principal;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.repository.UserRepository;

@RestController
@RequestMapping("/public")
public class PublicController {

  private static final Logger logger = Logger.getLogger(PublicController.class);

  private UserRepository userRepository;

  public PublicController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @RequestMapping("/self")
  public AstroAppUser getSelf(Principal principal) {
    if (null == principal) {
      return null;
    }
    return userRepository.findByEmail(principal.getName());
  }

  @PostConstruct
  public void postConstruct() {
    Assert.notNull(userRepository, "userRepository must be set");
  }
}
