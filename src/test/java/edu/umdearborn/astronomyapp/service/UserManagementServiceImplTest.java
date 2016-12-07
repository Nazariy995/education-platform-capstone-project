package edu.umdearborn.astronomyapp.service;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.repository.CourseRepository;
import edu.umdearborn.astronomyapp.repository.CourseUserRepository;
import edu.umdearborn.astronomyapp.repository.UserRepository;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class UserManagementServiceImplTest {

  @Mock
  private CourseRepository courseReopsitory;

  @Mock
  private CourseUserRepository courseUserRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private EmailService emailService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserManagementServiceImpl service;

  @Test
  public void persistNewUserTest() {
    when(userRepository.save(any(AstroAppUser.class))).then(returnsFirstArg());
    AstroAppUser actual = service.persistNewUser(new AstroAppUser());
    assertThat("Did not expire password", actual.isPasswordNonExpired(), equalTo(false));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void addUsersToCourseTest() {
    // Setup mock data
    Course course = new Course();
    course.setId("Course Id");
    List<CourseUser> users = new ArrayList<>(20);
    IntStream.range(0, 10).forEach(i -> {
      CourseUser existingCourseUser = new CourseUser();
      AstroAppUser existingUser = new AstroAppUser();
      existingUser.setEmail("exists" + i);
      existingCourseUser.setUser(existingUser);
      users.add(existingCourseUser);

      CourseUser nonExistantCourseUser = new CourseUser();
      AstroAppUser nonExistantUser = new AstroAppUser();
      nonExistantUser.setEmail("does not exist" + i);
      nonExistantCourseUser.setUser(nonExistantUser);
      users.add(nonExistantCourseUser);
    });
    Collections.shuffle(users);
    // Setup mock functionality
    when(userRepository.emailExists(matches("exists\\d"))).thenReturn(true);
    when(userRepository.save(any(AstroAppUser.class))).then(returnsFirstArg());
    when(courseUserRepository.save(any(Iterable.class))).thenReturn(new ArrayList<CourseUser>());
    when(emailService.buildEmailContext(any())).thenReturn(null);

    service.addUsersToCourse(course, users.toArray(new CourseUser[users.size()]));

    verify(userRepository, times(10)).save(any(AstroAppUser.class));
  }

}
