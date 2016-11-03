package edu.umdearborn.astronomyapp.util;

import java.util.ArrayList;
import java.util.Collection;

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

import edu.umdearborn.astronomyapp.entity.Role;
import edu.umdearborn.astronomyapp.entity.Role.RoleValue;
import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.repository.RoleRepository;
import edu.umdearborn.astronomyapp.repository.UserRepository;

@Configuration
@Transactional
public class StartupHousekeeper {
  
  private static final Logger logger = LoggerFactory.getLogger(StartupHousekeeper.class);
  
  private RoleRepository roleRepository;

  private UserRepository userRepository;
  
  private PasswordEncoder passwordEncoder;

  @Autowired
  public StartupHousekeeper(UserRepository userRepository, RoleRepository roleRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }
  
  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
    if (!roleRepository.adminExists()) {
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
      Collection<Role> roles = user.getRoles();
      if (roles == null) {
        roles = new ArrayList<Role>();
      }
      Role adminRole = new Role();
      adminRole.setRole(RoleValue.ADMIN);
      adminRole.setUser(user);
      roles.add(adminRole);
      user.setRoles(roles);
      userRepository.save(user);
    }
  }
  
  @PostConstruct
  public void onInit() {
    Assert.notNull(passwordEncoder);
    Assert.notNull(roleRepository);
    Assert.notNull(userRepository);
  }

}
