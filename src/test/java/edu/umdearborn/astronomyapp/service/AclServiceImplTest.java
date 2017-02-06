package edu.umdearborn.astronomyapp.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

import edu.umdearborn.astronomyapp.entity.CourseUser;

@RunWith(MockitoJUnitRunner.class)
public class AclServiceImplTest {

  private static final Logger logger = LoggerFactory.getLogger(AclServiceImplTest.class);

  @Mock
  private EntityManager entityManager;

  @Mock
  private TypedQuery<Boolean> query;

  @InjectMocks
  private AclServiceImpl service;

  @Before
  public void before() {
    when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(query);
    when(query.setParameter(anyString(), anyObject())).thenReturn(query);
  }

  @Test
  public void enforceInCourseTest() {
    when(query.getSingleResult()).thenReturn(true, true, false);

    try {
      service.enforceInCourse("enrolled", "courseId");
      service.enforceInCourse("enrolled", "courseId", "courseUserId");
    } catch (AccessDeniedException ex) {
      assertThat("Exception thrown", false);
      logger.error("Enforcing access conrol failed", ex);
    }

    try {
      service.enforceInCourse("not enrolled", "courseId", "courseUserId");
      assertThat("Exception should be thrown", false);
    } catch (AccessDeniedException ex) {
      assertThat("Exception thrown", true);
    }

    try {
      service.enforceInCourse("not enrolled", "courseId", "courseUserId");
      assertThat("Exception should be thrown", false);
    } catch (AccessDeniedException ex) {
      assertThat("Exception thrown", true);
    }

  }

  @Test
  public void enforceIsCourseRoleTest() {
    when(query.getSingleResult()).thenReturn(true, false);

    try {
      service.enforceIsCourseRole("good role", "courseId",
          Arrays.asList(CourseUser.CourseRole.STUDENT));
    } catch (AccessDeniedException ex) {
      assertThat("Exception thrown", false);
      logger.error("Enforcing access conrol failed", ex);
    }

    try {
      service.enforceIsCourseRole("bad role", "courseId", Arrays.asList(CourseUser.CourseRole.TA));
      assertThat("Exception should be thrown", false);
    } catch (AccessDeniedException ex) {
      assertThat("Exception thrown", true);
    }

  }

  @Test
  public void enforeInGroupTest() {
    when(query.getSingleResult()).thenReturn(true, false);

    try {
      service.enforceInGroup("in group", "groupDd");
    } catch (AccessDeniedException ex) {
      assertThat("Exception thrown", false);
      logger.error("Enforcing access conrol failed", ex);
    }

    try {
      service.enforceInGroup("not in group", "groupId");
      assertThat("Exception should be thrown", false);
    } catch (AccessDeniedException ex) {
      assertThat("Exception thrown", true);
    }

  }

}
