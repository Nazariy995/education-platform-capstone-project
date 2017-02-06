package edu.umdearborn.astronomyapp.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.umdearborn.astronomyapp.controller.exception.GroupAlterationException;
import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.entity.GroupMember;
import edu.umdearborn.astronomyapp.entity.Module;
import edu.umdearborn.astronomyapp.entity.ModuleGroup;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

  private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

  private EntityManager entityManager;

  public GroupServiceImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<CourseUser> joinGroup(String courseUserId, String moduleId, String groupId) {

    enforceNotInGroup(courseUserId, groupId);

    TypedQuery<ModuleGroup> query = entityManager
        .createQuery("select g from ModuleGroup g where g.id =  :groupId", ModuleGroup.class);
    query.setParameter("groupId", groupId);
    List<ModuleGroup> result = query.getResultList();

    ModuleGroup group = result.get(0);
    GroupMember member = new GroupMember();
    CourseUser user = new CourseUser();
    user.setId(courseUserId);
    member.setCourseUser(user);
    member.setModuleGroup(group);
    entityManager.persist(member);

    return getUsersInGroup(groupId);

  }

  @Override
  public ModuleGroup createGroup(String courseUserId, String moduleId) {

    enforceNotInGroup(courseUserId, moduleId);

    ModuleGroup group = new ModuleGroup();
    Module module = new Module();
    module.setId(moduleId);
    group.setModule(module);
    entityManager.persist(group);

    GroupMember member = new GroupMember();
    CourseUser user = new CourseUser();
    user.setId(courseUserId);
    member.setCourseUser(user);
    member.setModuleGroup(group);
    entityManager.persist(member);

    return group;
  }

  @Override
  public boolean isInAGroup(String courseUserId, String moduleId) {

    TypedQuery<Boolean> query = entityManager.createQuery(
        "select count(gm) > 0 from GroupMember gm join gm.moduleGroup g join mg.module m "
            + "join gm.courseUser cu where cu.id = :courseUserId and m.id = :moduleId",
        Boolean.class);
    query.setParameter("courseUserId", courseUserId);
    query.setParameter("moduleId", moduleId);

    return query.getSingleResult();
  }

  private void enforceNotInGroup(String courseUserId, String moduleId) {

    if (!isInAGroup(courseUserId, moduleId)) {
      logger.info("Course user: '{}' is already in a group for module: '{}'", courseUserId,
          moduleId);
      throw new GroupAlterationException(
          "Course user: " + courseUserId + " is already in a group for module: " + moduleId);
    }

    logger.debug("Course user: '{}' is not in a group for module: '{}'", courseUserId, moduleId);
  }

  @Override
  public List<CourseUser> getUsersInGroup(String groupId) {

    TypedQuery<CourseUser> query = entityManager.createQuery(
        "select cu from GroupMember gm join gm.moduleGroup g join gm.courseUser cu join cu.user u"
            + " where g.id = :groupId and cu.isActive = true and u.isEnabled = true",
        CourseUser.class);
    query.setParameter("groupId", groupId);

    return query.getResultList();
  }

}
