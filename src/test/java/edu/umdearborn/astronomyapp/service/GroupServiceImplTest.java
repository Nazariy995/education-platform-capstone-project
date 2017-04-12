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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umdearborn.astronomyapp.controller.exception.GroupAlterationException;
import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.entity.ModuleGroup;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class GroupServiceImplTest {

  private static final Logger logger = LoggerFactory.getLogger(GroupServiceImplTest.class);

  @Mock
  private EntityManager entityManager;

  @Mock
  private TypedQuery<Boolean> booleanQuery;

  @Mock
  private TypedQuery<ModuleGroup> groupQuery;

  @Mock
  private TypedQuery<CourseUser> courseUserQuery;

  @InjectMocks
  private GroupServiceImpl service;

  @Before
  public void before() {
    when(entityManager.createQuery(anyString(), eq(Boolean.class))).thenReturn(booleanQuery);
    when(booleanQuery.setParameter(anyString(), anyObject())).thenReturn(booleanQuery);

    when(entityManager.createQuery(anyString(), eq(ModuleGroup.class))).thenReturn(groupQuery);
    when(groupQuery.setParameter(anyString(), anyObject())).thenReturn(groupQuery);

    when(entityManager.createQuery(anyString(), eq(CourseUser.class))).thenReturn(courseUserQuery);
    when(courseUserQuery.setParameter(anyString(), anyObject())).thenReturn(courseUserQuery);
  }

  @Test
  public void joinGroupTest() {
    when(booleanQuery.getSingleResult()).thenReturn(false, false, true);
    when(groupQuery.getResultList()).thenReturn(Arrays.asList(new ModuleGroup()));
    when(courseUserQuery.getResultList()).thenReturn(Arrays.asList(new CourseUser()));

    try {
      service.joinGroup("not in group", "moduleId", "groupId");
    } catch (GroupAlterationException ex) {
      assertThat("Exception thrown", false);
      logger.error("Joining group failed", ex);
    }

    try {
      service.joinGroup("in group", "moduleId", "groupId");
      assertThat("Exception not thrown", false);
    } catch (GroupAlterationException ex) {
      assertThat("Exception thrown", true);
    }

  }

  @Test
  public void createGroupTest() {
    when(booleanQuery.getSingleResult()).thenReturn(false, false, true);

    try {
      service.createGroup("not in group", "moduleId");
    } catch (GroupAlterationException ex) {
      assertThat("Exception thrown", false);
      logger.error("Joining group failed", ex);
    }

    try {
      service.createGroup("in group", "moduleId");
      assertThat("Exception not thrown", false);
    } catch (GroupAlterationException ex) {
      assertThat("Exception thrown", true);
    }

  }
}
