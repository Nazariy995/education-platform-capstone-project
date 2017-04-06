package edu.umdearborn.astronomyapp.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.entity.CourseUser.CourseRole;

@Service
@Transactional
public class AclServiceImpl implements AclService {

  private static final Logger logger = LoggerFactory.getLogger(AclServiceImpl.class);

  private EntityManager entityManager;

  public AclServiceImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Cacheable
  @Override
  public void enforceInCourse(String email, String courseId) {

    TypedQuery<Boolean> query = entityManager.createQuery(
        "select count(cu) > 0 from CourseUser cu join cu.user u join cu.course c where "
            + "lower(u.email) = lower(:email) and c.id = :courseId and "
            + "cu.isActive = true and u.isEnabled = true",
        Boolean.class);
    query.setParameter("email", email).setParameter("courseId", courseId);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("{} does not have access to course: '{}'", email, courseId);
      throw new AccessDeniedException(email + " cannot access " + courseId);
    }

    logger.debug("{} has access to course: '{}'", email, courseId);
  }

  @Cacheable
  @Override
  public void enforceIsCourseRole(String email, String courseId, List<CourseUser.CourseRole> role) {

    TypedQuery<Boolean> query = entityManager
        .createQuery("select count(cu) > 0 from CourseUser cu join cu.user u join cu.course c "
            + "where c.id = :courseId and cu.isActive = true and u.isEnabled = true "
            + "and lower(u.email) = lower(:email) and cu.role in (:role)", Boolean.class);
    query.setParameter("email", email).setParameter("courseId", courseId).setParameter("role",
        role);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("{} does not have any role in {} in course: '{}'", email, role, courseId);
      throw new AccessDeniedException(email + " is not a " + role + " in course " + courseId);
    }

    logger.debug("{} does have any role in {} in course: '{}'", email, role, courseId);
  }

  @Cacheable
  @Override
  public void enforceInGroup(String courseUserId, String groupId) {

    TypedQuery<Boolean> query = entityManager.createQuery(
        "select count(gm) > 0 from GroupMember gm join gm.courseUser cu join gm.moduleGroup g "
            + "where cu.id = :courseUserId and g.id = :groupId",
        Boolean.class);
    query.setParameter("courseUserId", courseUserId).setParameter("groupId", groupId);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("Course User: '{}' cannot alter group: '{}'", courseUserId, groupId);
      throw new AccessDeniedException(
          "Course user: " + courseUserId + " cannot alter group: " + groupId);
    }

    logger.debug("Course User: '{}' can alter group: '{}'", courseUserId, groupId);

  }

  @Cacheable
  @Override
  public void enforceInCourse(String email, String courseId, String courseUserId) {
    TypedQuery<Boolean> query =
        entityManager.createQuery(
            "select count(cu) > 0 from CourseUser cu join cu.user u join cu.course c where "
                + "lower(u.email) = lower(:email) and c.id = :courseId and "
                + "cu.isActive = true and u.isEnabled = true and cu.id = :courseUserId",
            Boolean.class);
    query.setParameter("courseUserId", courseUserId).setParameter("courseId", courseId)
        .setParameter("email", email);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("{} does not have access to course: '{}'", email, courseId);
      throw new AccessDeniedException(email + " cannot access " + courseId);
    }

    logger.debug("{} has access to course: '{}'", email, courseId);

  }

  @Cacheable
  @Override
  public void enforceModuleVisible(String moduleId) {

    TypedQuery<Boolean> query =
        entityManager.createQuery("select count (m) > 0 from Module m where m.id = :moduleId and "
            + "m.visibleTimestamp <= current_timestamp()", Boolean.class);
    query.setParameter("moduleId", moduleId);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("Cannot open module: '{}'", moduleId);
      throw new AccessDeniedException("Cannot open module: " + moduleId);
    }

    logger.debug("Can open module: '{}'", moduleId);

  }

  @Cacheable
  @Override
  public void enforceModuleOpen(String moduleId) {

    TypedQuery<Boolean> query =
        entityManager.createQuery("select count (m) > 0 from Module m where m.id = :moduleId and "
            + "m.openTimestamp <= current_timestamp()", Boolean.class);
    query.setParameter("moduleId", moduleId);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("Cannot open module: '{}'", moduleId);
      throw new AccessDeniedException("Cannot open module: " + moduleId);
    }

    logger.debug("Can perform actions for module: '{}'", moduleId);

  }

  @Cacheable
  @Override
  public void enforeceModuleInCourse(String courseId, String moduleId) {

    TypedQuery<Boolean> query =
        entityManager.createQuery("select count (m) > 0 from Module m join m.course c "
            + "where m.id = :moduleId and c.id = :courseId", Boolean.class);
    query.setParameter("moduleId", moduleId).setParameter("courseId", courseId);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("Module: '{}' not part of course: '{}'", moduleId, courseId);
      throw new AccessDeniedException("Module: " + moduleId + " not part of course: " + courseId);
    }

    logger.debug("Module: '{}' is part of course: '{}'", moduleId, courseId);

  }

  @Cacheable
  @Override
  public void enforceGroupInCourse(String groupId, String courseId) {

    TypedQuery<Boolean> query =
        entityManager.createQuery("select count (g) > 0 from ModuleGroup g join g.module m join "
            + "m.course c where g.id = :groupId and c.id = :courseId", Boolean.class);
    query.setParameter("groupId", groupId).setParameter("courseId", courseId);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("Group: '{}' not part of course: '{}'", groupId, courseId);
      throw new AccessDeniedException("Group: " + groupId + " not part of course: " + courseId);
    }

    logger.debug("Group: '{}' is part of course: '{}'", groupId, courseId);

  }

  @Override
  public void enforceHasLock(String groupId, List<String> checkedIn) {

    TypedQuery<Boolean> query = entityManager.createQuery(
        "select count(cu) = 0 from GroupMember gm join gm.moduleGroup g join gm.courseUser cu "
            + "join cu.user u where g.id = :groupId and cu.isActive = true and "
            + "u.isEnabled = true and cu.id not in (:checkedIn)",
        Boolean.class);
    query.setParameter("groupId", groupId).setParameter("checkedIn", checkedIn);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("Lock not aquired for group: '{}'", groupId);
      throw new AccessDeniedException("Lock not aquired for group: " + groupId);
    }

    logger.debug("Lock aquired for group: '{}'", groupId);

  }

  @Override
  public void enforceGroupLocked(String groupId, boolean shouldBeLocked) {

    TypedQuery<Boolean> query = entityManager
        .createQuery("select count(g) > 0 from ModuleGroup g where g.id = :groupId and "
            + "g.isLocked = :shouldBeLocked", Boolean.class)
        .setParameter("groupId", groupId).setParameter("shouldBeLocked", shouldBeLocked);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("Group: '{}' finalized status not valid", groupId);
      throw new AccessDeniedException("Group: " + groupId + " finalized status not valid");
    }

    logger.debug("Group: '{}' finalized status is valid", groupId);
  }

  @Cacheable
  @Override
  public void enforceHasRoleInCourse(String courseUserId, String courseId, List<CourseRole> role) {
    TypedQuery<Boolean> query = entityManager.createQuery(
        "select count(cu) > 0 from CourseUser cu join cu.course c "
            + "where c.id = :courseId and cu.id = :courseUserId and cu.role in (:role)",
        Boolean.class);
    query.setParameter("courseUserId", courseUserId).setParameter("courseId", courseId)
        .setParameter("role", role);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("{} does not have any role in {} in course: '{}'", courseUserId, role, courseId);
      throw new AccessDeniedException(
          courseUserId + " is not a " + role + " in course " + courseId);
    }

    logger.debug("{} does have any role in {} in course: '{}'", courseUserId, role, courseId);

  }

  @Override
  public void enforceCourseNotOpen(String courseId) {
    TypedQuery<Boolean> query = entityManager.createQuery(
        "select count(c) > 0 from Course c where c.openTimestamp > now() and c.id = :id",
        Boolean.class);
    query.setParameter("id", courseId);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("Course: '{}' is already open", courseId);
      throw new AccessDeniedException("Course: " + courseId + " is already open");
    }

    logger.debug("Course: '{}' is not open", courseId);
  }

  @Override
  public void enforceModuleNotOpen(String moduleId) {
    TypedQuery<Boolean> query = entityManager.createQuery(
        "select count(m) > 0 from Module m where m.id = :id and (m.openTimestamp > now() or "
            + "m.openTimestamp is null)",
        Boolean.class);
    query.setParameter("id", moduleId);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("Module: '{}' is already open", moduleId);
      throw new AccessDeniedException("Module: " + moduleId + " is already open");
    }

    logger.debug("Module: '{}' is not open", moduleId);
  }

  @Override
  public void enforceModuleClosed(String moduleId) {
    TypedQuery<Boolean> query = entityManager.createQuery(
        "select count(m) > 0 from Module m where m.closeTimestamp < now() and m.id = :id",
        Boolean.class);
    query.setParameter("id", moduleId);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("Module: '{}' is still open", moduleId);
      throw new AccessDeniedException("Module: " + moduleId + " is still open");
    }

    logger.debug("Module: '{}' is closed", moduleId);
  }

  @Override
  public void enforeceCourseNotClosed(String courseId) {
    TypedQuery<Boolean> query = entityManager.createQuery(
        "select count(c) > 0 from Course c where c.closeTimestamp > now() and c.id = :id",
        Boolean.class);
    query.setParameter("id", courseId);
    boolean result = query.getSingleResult();

    if (!result) {
      logger.debug("Course: '{}' is closed", courseId);
      throw new AccessDeniedException("Course: " + courseId + " is closed");
    }

    logger.debug("Course: '{}' is not closed", courseId);

  }

  @Override
  public void enforeModuleNotClosed(String moduleId) {
    if (!entityManager.createQuery(
        "select count(m) > 0 from Module m where m.closeTimestamp > now() and m.id = :id",
        Boolean.class).setParameter("id", moduleId).getSingleResult()) {
      logger.debug("Module: '{}' is closed", moduleId);
      throw new AccessDeniedException("Module: " + moduleId + " is closed");
    }

    logger.debug("Module: '{}' is not closed", moduleId);

  }

}
