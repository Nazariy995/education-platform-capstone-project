package edu.umdearborn.astronomyapp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

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
        "select count(gm) > 0 from GroupMember gm join gm.moduleGroup g join g.module m "
            + "join gm.courseUser cu where cu.id = :courseUserId and m.id = :moduleId",
        Boolean.class);
    query.setParameter("courseUserId", courseUserId).setParameter("moduleId", moduleId);

    return query.getSingleResult();
  }

  private void enforceNotInGroup(String courseUserId, String moduleId) {

    TypedQuery<Boolean> query = entityManager.createQuery(
        "select count(u) > 0 from CourseUser u where u.id = :courseUserId and u.role != :role",
        Boolean.class);
    query.setParameter("courseUserId", courseUserId).setParameter("role",
        CourseUser.CourseRole.STUDENT);
    boolean isNotStudentRole = query.getSingleResult();

    if (isInAGroup(courseUserId, moduleId) || isNotStudentRole) {
      logger.info("Course user: '{}' cannot join group for module: '{}'", courseUserId, moduleId);
      throw new GroupAlterationException(
          "Course user: " + courseUserId + " cannot join group for module: " + moduleId);
    }

    logger.debug("Course user: '{}' can join group for module: '{}'", courseUserId, moduleId);
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
        .createQuery("select mg from GroupMember gm join gm.moduleGroup mg join gm.courseUser cu "
            + "join mg.module m where cu.id = :courseUserId and "
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
  public List<Answer> saveAnswers(Map<String, String> answers, String groupId) {

    List<Answer> savedAnswers = getAnswers(groupId, true);
    if (ResultListUtil.hasResult(savedAnswers)) {

      ModuleGroup group = new ModuleGroup();
      group.setId(groupId);

      for (String key : answers.keySet()) {
        savedAnswers.parallelStream().filter(a -> a.getQuestion().getId() == key).findAny()
            .ifPresent(a -> {
              a.setValue(answers.get(key));
            });
      }

      return getAnswers(groupId, true);
    }

    return null;
  }

  @Override
  public Long submissionNumber(String groupId) {

    TypedQuery<Long> query = entityManager.createQuery(
        "select max(a.submissionNumber) from Answer a join a.group g join g.module m where "
            + "g.groupId = :groupId",
        Long.class);
    query.setParameter("groupId", groupId);
    List<Long> result = query.getResultList();

    if (ResultListUtil.hasResult(result)) {
      return result.get(0);
    }

    return null;
  }

  @Override
  public List<Answer> getAnswers(String groupId, boolean getSavedAnswers) {
    StringBuilder jpql = new StringBuilder(
        "select Answer a join a.group g join g.module m where g.id = :groupId and "
            + "a.submissionNumber = :submissionNumber");

    if (!getSavedAnswers) {
      jpql.append(" and a.submissionDate is not null");
    }

    TypedQuery<Answer> query = entityManager.createQuery(jpql.toString(), Answer.class);
    query.setParameter("groupId", groupId);

    if (getSavedAnswers) {
      query.setParameter("submissionNumber", 0);
    } else {
      query.setParameter("submissionNumber", submissionNumber(groupId));
    }

    return query.getResultList();
  }

  @Override
  public void finalizeGroup(String groupId) {
    entityManager
        .createQuery("update ModuleGroup g set g.isLocked = true where g.groupId = :groupId")
        .executeUpdate();

    TypedQuery<String> questionIdQuery = entityManager.createQuery(
        "select q.id from Question q join q.page p join p.module m where m.id = (select m.id "
            + "from ModuleGroup g join g.module m where g.id = :groupId)",
        String.class);
    questionIdQuery.setParameter("groupId", groupId);
    List<String> questionIdsResult = questionIdQuery.getResultList();

    ModuleGroup group = new ModuleGroup();
    group.setId(groupId);
    Question question = new Question();
    if (ResultListUtil.hasResult(questionIdsResult)) {
      logger.debug("Creating answers...");

      questionIdsResult.parallelStream().map(questionId -> {
        Answer answer = new Answer();
        answer.setGroup(group);
        question.setId(questionId);
        answer.setQuestion(question);
        return answer;
      }).forEach(entityManager::persist);

    }

  }

  @Override
  public List<Answer> submitAnswers(String groupId) {
    List<Answer> answers = getAnswers(groupId, true);

    if (ResultListUtil.hasResult(answers)) {
      Date date = new Date();
      answers.parallelStream().map(a -> {
        a.setId(null);
        a.setSubmissionNumber(submissionNumber(groupId) + 1);
        a.setSubmissionTimestamp(date);
        return a;
      }).forEach(entityManager::persist);;
    }

    return getAnswers(groupId, false);
  }

  @Override
  public List<CourseUser> removeFromGroup(String groupId, String courseUserId) {
    entityManager
        .createQuery(
            "remove GroupMember m join m.group g join m.courseUser u where g.id = :groupId and "
                + "u.id = :courseUserId")
        .setParameter("groupId", groupId).setParameter("courseUserId", courseUserId)
        .executeUpdate();

    TypedQuery<Boolean> isGroupEmptyQuery = entityManager
        .createQuery("select count(m) > 0 from GroupMember m join m.group g where g.id = :groupId",
            Boolean.class)
        .setParameter("groupId", groupId);
    boolean isGroupEmpty = isGroupEmptyQuery.getSingleResult();

    if (isGroupEmpty) {
      entityManager.createQuery("remove ModuleGroup g where g.id = :groupId")
          .setParameter("groupId", groupId).executeUpdate();

      return null;
    }

    return getUsersInGroup(groupId);
  }

  @Override
  public List<CourseUser> getFreeUsers(String courseId, String moduleId) {

    TypedQuery<CourseUser> query =
        entityManager
            .createQuery(
                "select u from CourseUser u join u.course c where c.id = :courseId and u.id not in "
                    + "(select cu.id from GroupMember gm join gm.moduleGroup g join g.module m join "
                    + "gm.courseUser cu where m.id = :moduleId) and u.role = 'STUDENT'",
                CourseUser.class)
            .setParameter("courseId", courseId).setParameter("moduleId", moduleId);

    return query.getResultList();
  }
}
