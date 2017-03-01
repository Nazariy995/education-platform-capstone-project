package edu.umdearborn.astronomyapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.util.email.NewUserEmailContextBuilder;

@Service
@Transactional
public class UserManagementServiceImpl implements UserManagementService {

  private EntityManager entityManager;
  private EmailService emailService;
  private PasswordEncoder passwordEncoder;

  public UserManagementServiceImpl(EntityManager entityManager, EmailService emailService, PasswordEncoder passwordEncoder) {
    this.entityManager = entityManager;
    this.emailService = emailService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public List<CourseUser> addUsersToCourse(Course course, CourseUser... users) {
    List<CourseUser> userList = StreamSupport.stream(Arrays.spliterator(users), false).peek(u -> {
      if (!emailExists(u.getUser().getEmail())) {
        u.setUser(persistNewUser(u.getUser()));
      }
    }).collect(Collectors.toList());

    return null;
  }

  public boolean emailExists(String email) {
    return entityManager
        .createQuery("select count(u) > 0 from AstroAppUser u where lower(u.email) = lower(:email)",
            Boolean.class)
        .setParameter("email", email).getSingleResult();
  }

  @Override
  public List<CourseUser> addUsersToCourse(String courseId, CourseUser... users) {
    return null;
  }

  @Override
  public AstroAppUser persistNewUser(AstroAppUser user) {

    user.setPasswordNonExpired(false);
    user.setPassword(RandomStringUtils.randomAlphanumeric(12));

    Map<String, String> context =
        emailService.buildEmailContext(new NewUserEmailContextBuilder(user));
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    emailService.send(context);

    entityManager.persist(user);
    
    return user;
  }

  @PostConstruct
  public void postConstruct() {
    Assert.notNull(emailService);
    Assert.notNull(passwordEncoder);
  }

  @Override
  public AstroAppUser updateUser(AstroAppUser user) {
    return null;
  }

}
