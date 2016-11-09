package edu.umdearborn.astronomyapp.util;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.AstroAppUser.Role;
import edu.umdearborn.astronomyapp.repository.UserRepository;

@Configuration
@Transactional
public class StartupHousekeeper {

  private static final Logger logger = LoggerFactory.getLogger(StartupHousekeeper.class);

  private UserRepository userRepository;

  private PasswordEncoder passwordEncoder;

  @Autowired
  public StartupHousekeeper(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
    if (!userRepository.adminExists()) {
      logger.warn("Admin role does not exist!");
      AstroAppUser user = userRepository.findByEmail("admin");
      if (user == null) {
        logger.warn("Creating admin user");
        user = new AstroAppUser();
        user.setEmail("admin");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setPasswordHash(passwordEncoder.encode("admin"));
      }
      user.getRoles().add(Role.ADMIN);
      userRepository.save(user);
    }
  }

  @PostConstruct
  public void onInit() {
    Assert.notNull(passwordEncoder);
    Assert.notNull(userRepository);
  }

}
