package edu.umdearborn.astronomyapp.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.umdearborn.astronomyapp.controller.exception.GroupAlterationException;
import edu.umdearborn.astronomyapp.entity.Answer;
import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.entity.GroupMember;
import edu.umdearborn.astronomyapp.entity.Module;
import edu.umdearborn.astronomyapp.entity.ModuleGroup;
import edu.umdearborn.astronomyapp.entity.Question;
import edu.umdearborn.astronomyapp.util.ResultListUtil;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

  private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

  private EntityManager   entityManager;
  private PasswordEncoder passwordEncoder;

  public GroupServiceImpl(EntityManager entityManager, PasswordEncoder passwordEncoder) {
    this.entityManager = entityManager;
    this.passwordEncoder = passwordEncoder;
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
    query.setParameter("courseUserId", courseUserId).setParameter("moduleId", moduleId);

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

  @Override
  public ModuleGroup getGroup(String courseUserId, String moduleId) {

    TypedQuery<ModuleGroup> query = entityManager
        .createQuery("select g from GroupMember gm join gm.moduleGroup g join gm.courseUser cu "
            + "join gm.moduleGroup mg join mg.module m where cu.id = :courseUserId and "
            + "cu.isActive = true and m.id = :moduleId", ModuleGroup.class);
    query.setParameter("courseUserId", courseUserId).setParameter("moduleId", moduleId);
    List<ModuleGroup> result = query.getResultList();

    if (ResultListUtil.hasResult(result)) {
      return result.get(0);
    }

    return null;
  }

  @Override
  public CourseUser checkin(String email, String password, String groupId) {

    TypedQuery<CourseUser> query = entityManager.createQuery(
        "select cu from GroupMember gm join gm.courseUser cu join fetch cu.user u join "
            + "gm.moduleGroup g where g.id = :groupId and lower(u.email) = lower(:email) and "
            + "cu.isActive = true and u.isEnabled = true",
        CourseUser.class);
    query.setParameter("email", email).setParameter("groupId", groupId);
    List<CourseUser> result = query.getResultList();

    if (ResultListUtil.hasResult(result)
        && passwordEncoder.matches(password, result.get(0).getUser().getPassword())) {
      return result.get(0);
    }

    return null;
  }

  @Override
  public boolean hasLock(String groupId, List<String> checkedIn) {

    TypedQuery<Boolean> query = entityManager.createQuery(
        "select count(cu) = 0 from GroupMember gm join gm.moduleGroup g join gm.courseUser cu "
            + "join cu.user u where g.id = :groupId and cu.isActive = true and "
            + "u.isEnabled = true and cu.id not in (:checkedIn)",
        Boolean.class);
    query.setParameter("groupId", groupId).setParameter("checkedIn", checkedIn);

    return query.getSingleResult();
  }

  @Override
  public Map<String, String> saveAnswers(Map<String, String> answers, String groupId) {

    Answer answer;
    String value;
    Question question;
    ModuleGroup group = new ModuleGroup();
    group.setId(groupId);

    for (String key : answers.keySet()) {
      value = StringUtils.trimToEmpty(answers.get(key));
      if (!value.isEmpty()) {
        answer = new Answer();
        answer.setValue(value);

        question = new Question();
        question.setId(key);
        answer.setQuestion(question);

        answer.setGroup(group);

        entityManager.persist(answer);

        answers.put(key, answer.getId());
      } else {
        answers.remove(key);
      }
    }

    return answers;
  }

}
