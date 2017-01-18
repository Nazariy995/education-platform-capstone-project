package edu.umdearborn.astronomyapp.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

import edu.umdearborn.astronomyapp.repository.CourseUserRepository;

@RunWith(MockitoJUnitRunner.class)
public class AclServiceImplTest {

  private static final Logger logger = LoggerFactory.getLogger(AclServiceImplTest.class);

  @Mock
  private CourseUserRepository courseUserRepository;

  @InjectMocks
  private AclServiceImpl service;

  @Test // (expected = AccessDeniedException.class)
  public void courseUserAclTest() {
    when(courseUserRepository.isInCourse(anyString(), anyString())).thenReturn(true, false, true);

    try {
      service.enforceCourse("enrolled", "courseId");
    } catch (AccessDeniedException ex) {
      assertThat("Exception thrown", false);
      logger.error("Enforcing access conrol failed", ex);
    }

    try {
      service.enforceCourse("not enrolled", "courseId");
      assertThat("Exception should be thrown", false);
    } catch (AccessDeniedException ex) {
      assertThat("Exception thrown", true);
    }

    try {
      service.enforceCourse("enrolled", "  ");
      assertThat("Exception should be thrown", false);
    } catch (AccessDeniedException ex) {
      assertThat("Exception thrown", true);
    }

  }

}
