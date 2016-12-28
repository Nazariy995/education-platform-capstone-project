package edu.umdearborn.astronomyapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.repository.CourseRepository;
import edu.umdearborn.astronomyapp.repository.CourseUserRepository;
import edu.umdearborn.astronomyapp.repository.UserRepository;
import edu.umdearborn.astronomyapp.util.email.NewUserEmailContextBuilder;

@Service
@Transactional
public class UserManagementServiceImpl implements UserManagementService {

  private CourseRepository courseRepository;

  private CourseUserRepository courseUserRepository;

  private EmailService emailService;

  private PasswordEncoder passwordEncoder;

  private UserRepository userRepository;

  public UserManagementServiceImpl(UserRepository userRepository, CourseRepository courseRepository,
      CourseUserRepository courseUserRepository, EmailService emailService,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.courseRepository = courseRepository;
    this.courseUserRepository = courseUserRepository;
    this.emailService = emailService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public List<CourseUser> addUsersToCourse(Course course, CourseUser... users) {
    List<CourseUser> userList = StreamSupport.stream(Arrays.spliterator(users), true).peek(u -> {
      if (!userRepository.emailExists(u.getUser().getEmail())) {
        u.setUser(persistNewUser(u.getUser()));
      }
    }).collect(Collectors.toList());

    return courseUserRepository.save(userList);
  }

  @Override
  public List<CourseUser> addUsersToCourse(String courseId, CourseUser... users) {
    return addUsersToCourse(courseRepository.findOne(courseId), users);
  }

  @Override
  public AstroAppUser persistNewUser(AstroAppUser user) {
    Assert.isNull(user.getId(), "Attempting to persist existing user as new user");
    Assert.isTrue(!userRepository.emailExists(user.getEmail()), "Email already exists");

    user.setPasswordNonExpired(false);
    user.setPassword(RandomStringUtils.randomAlphanumeric(12));

    Map<String, String> context =
        emailService.buildEmailContext(new NewUserEmailContextBuilder(user));
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    emailService.send(context);

    return userRepository.save(user);
  }

  @PostConstruct
  public void postConstruct() {
    Assert.notNull(userRepository);
    Assert.notNull(courseRepository);
    Assert.notNull(courseUserRepository);
    Assert.notNull(emailService);
    Assert.notNull(passwordEncoder);
  }

  @Override
  public AstroAppUser updateUser(AstroAppUser user) {
    Assert.notNull(user.getId(), "Attempting to update non-existant user");
    return userRepository.save(user);
  }

}
