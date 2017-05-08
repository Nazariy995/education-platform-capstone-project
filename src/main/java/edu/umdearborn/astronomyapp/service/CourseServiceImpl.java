package edu.umdearborn.astronomyapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.umdearborn.astronomyapp.entity.AbstractGeneratedId;
import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;
import edu.umdearborn.astronomyapp.entity.CourseUser.CourseRole;
import edu.umdearborn.astronomyapp.entity.Module;
import edu.umdearborn.astronomyapp.entity.MultipleChoiceQuestion;
import edu.umdearborn.astronomyapp.entity.NumericQuestion;
import edu.umdearborn.astronomyapp.entity.Page;
import edu.umdearborn.astronomyapp.entity.PageItem;
import edu.umdearborn.astronomyapp.entity.PageItem.PageItemType;
import edu.umdearborn.astronomyapp.entity.Question;
import edu.umdearborn.astronomyapp.util.ResultListUtil;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

  private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

  private EntityManager entityManager;

  public CourseServiceImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public CourseUser getCourseUser(String email, String courseId) {

    TypedQuery<CourseUser> query = entityManager
        .createQuery("select cu from CourseUser cu join fetch cu.user u join cu.course c  "
            + "where u.isEnabled = true and cu.isActive = true and "
            + "lower(u.email) = lower(:email) and c.id = :courseId and u.isEnabled = true "
            + "and cu.isActive = true", CourseUser.class);
    query.setParameter("email", email).setParameter("courseId", courseId);
    List<CourseUser> result = query.getResultList();

    if (ResultListUtil.hasResult(result)) {
      return result.get(0);
    }

    return null;
  }

  @Override
  public List<Course> getCourses(String email, boolean hideClosed, boolean hideOpenSoon) {
    StringBuilder jpql =
        new StringBuilder("select c from CourseUser cu join cu.user u join cu.course c "
            + "where lower(u.email) = lower(:email) and cu.isActive = true "
            + "and u.isEnabled = true");

    if (hideClosed) {
      logger.debug("Hiding already closed courses");
      jpql.append(" and c.closeTimestamp >= current_timestamp()");
    }

    if (hideOpenSoon) {
      logger.debug("Hiding courses that are not yet open");
      jpql.append(" and c.openTimestamp <= current_timestamp()");
    }

    logger.debug("Resuting JPQL: {}", jpql.toString());
    TypedQuery<Course> query = entityManager.createQuery(jpql.toString(), Course.class);
    query.setParameter("email", email);

    return query.getResultList();
  }

  @Override
  public Course createCourse(Course course, String email) {
    entityManager.persist(course);
    CourseUser courseUser = new CourseUser();
    courseUser.setCourse(course);
    courseUser.setRole(CourseRole.INSTRUCTOR);
    courseUser.setUser(entityManager
        .createQuery("select u from AstroAppUser u where upper(u.email) = upper(:email)",
            AstroAppUser.class)
        .setParameter("email", email).getSingleResult());
    entityManager.persist(courseUser);
    return course;
  }

  @Override
  public Course updateCourse(Course course) {
    Course old = entityManager.find(Course.class, course.getId());
    old = course;
    entityManager.merge(old);
    return old;
  }

  @Override
  public List<CourseUser> getClassList(String courseId, List<CourseUser.CourseRole> roles) {

    TypedQuery<CourseUser> query = entityManager
        .createQuery("select cu from CourseUser cu join fetch cu.user u join cu.course c "
            + "where u.isEnabled = true and cu.isActive = true and cu.role in :roles "
            + "and c.id = :courseId", CourseUser.class);
    query.setParameter("roles", roles).setParameter("courseId", courseId);

    return query.getResultList();
  }

  @Override
  public Course getCourseDetails(String courseId) {

    TypedQuery<Course> query =
        entityManager.createQuery("select c from Course c where c.id = :courseId", Course.class);
    query.setParameter("courseId", courseId);
    List<Course> result = query.getResultList();

    if (ResultListUtil.hasResult(result)) {
      return result.get(0);
    }

    return null;
  }

  @Override
  public Course clone(Course course, String cloneFromId, String email) {
    course = createCourse(course, email);

    List<Module> modules = new ArrayList<>();
    List<Page> pages = new ArrayList<>();
    List<PageItem> pageItems = new ArrayList<>();

    Optional.ofNullable(entityManager
        .createQuery("select m from Module m join m.course c where c.id = :courseId", Module.class)
        .setParameter("courseId", cloneFromId).getResultList()).ifPresent(e -> modules.addAll(e));

    Optional.ofNullable(entityManager
        .createQuery("select p from Page p join p.module m where m.id in :moduleIds", Page.class)
        .setParameter("moduleIds", getIds(modules)).getResultList())
        .ifPresent(e -> pages.addAll(e));

    Optional
        .ofNullable(entityManager
            .createQuery("select i from PageItem i join i.page p where p.id in :pageIds",
                PageItem.class)
            .setParameter("pageIds", getIds(pages)).getResultList())
        .ifPresent(e -> pageItems.addAll(e));

    migrate(course, modules, pages, pageItems);

    return course;
  }

  @Override
  public List<Course> getCourses() {
    return entityManager.createQuery("select c from Course c", Course.class).getResultList();
  }

  @Override
  public Course getCourse(String id) {
    return entityManager.find(Course.class, id);
  }

  private void migrate(Course course, List<Module> modules, List<Page> pages,
      List<PageItem> pageItems) {

    entityManager.clear();

    logger.info("Migrating {} modules into new course: '{}'", modules.size(), course.getId());
    migrateModules(course, modules);

    logger.info("Migrating {} pages into new course: '{}'", pages.size(), course.getId());
    migratePages(pages);

    entityManager.flush();

    logger.info("Migrating {} page items into new course: '{}'", pageItems.size(), course.getId());
    migratePageItems(pageItems);

  }

  private void migrateModules(Course course, List<Module> modules) {

    modules.stream().forEach(e -> {
      logger.debug("Migrating module: '{}'", e.getId());
      e.setId(null);
      e.setDueDate(null);
      e.setCloseTimestamp(null);
      e.setVisibleTimestamp(null);
      e.setOpenTimestamp(null);
      e.setCourse(course);
      entityManager.persist(e);
    });

  }

  private void migratePages(List<Page> pages) {

    pages.stream().forEach(e -> {
      logger.debug("Migrating page: '{}'", e.getId());
      e.setId(null);
      entityManager.persist(e);
    });

  }

  private void migratePageItems(List<PageItem> pageItems) {

    pageItems.stream().forEach(e -> {
      String oldId = e.getId();
      logger.debug("Migrating page item: '{}'", oldId);

      e.prePersist();

      entityManager
          .createNativeQuery(
              "insert into page_item (page_item_id, page_id, page_item_type, item_order, "
                  + "human_readable_text) values (?, ?, ?, ?, ?)")
          .setParameter(1, e.getId()).setParameter(2, e.getPage().getId())
          .setParameter(3, String.valueOf(e.getPageItemType())).setParameter(4, e.getOrder())
          .setParameter(5, e.getHumanReadableText()).executeUpdate();

      if (PageItemType.QUESTION.equals(e.getPageItemType())) {

        entityManager
            .createNativeQuery("insert into question("
                + "default_comment, is_gatekeeper, points, question_type, question_id)"
                + "values (?, ?, ?, ?, ?)")
            .setParameter(1, ((Question) e).getDefaultComment())
            .setParameter(2, ((Question) e).isGatekeeper())
            .setParameter(3, ((Question) e).getPoints())
            .setParameter(4, String.valueOf(((Question) e).getQuestionType()))
            .setParameter(5, e.getId()).executeUpdate();

        switch (((Question) e).getQuestionType()) {
          case MULTIPLE_CHOICE:
            logger.debug("Migrating options for question: '{}'", oldId);

            MultipleChoiceQuestion mcq = entityManager
                .createQuery("select q from MultipleChoiceQuestion q join fetch q.options "
                    + "where q.id = :id", MultipleChoiceQuestion.class)
                .setParameter("id", oldId).getSingleResult();
            entityManager.detach(mcq);
            mcq.setPage(e.getPage());

            logger.debug("Inserting into multiple choice");
            entityManager.createNativeQuery(
                "INSERT INTO multiple_choice_question(multiple_choice_question_id) " + "VALUES (?)")
                .setParameter(1, e.getId()).executeUpdate();

            logger.debug("Inserting multiple choice options");
            mcq.getOptions().stream().forEach(f -> {
              f.prePersist();
              entityManager
                  .createNativeQuery("INSERT INTO multiple_choice_option("
                      + "multiple_choice_option_id, is_correct_option, human_readable_text, option_question_id)"
                      + "VALUES (?, ?, ?, ?)")
                  .setParameter(1, f.getId()).setParameter(2, f.isCorrectOption())
                  .setParameter(3, f.getHumanReadableText()).setParameter(4, e.getId())
                  .executeUpdate();
            });

            break;
          case NUMERIC:
            logger.debug("Migrating options for question: '{}'", oldId);

            NumericQuestion nq = entityManager.createQuery(
                "select q from NumericQuestion q join fetch q.options where q.id = :id",
                NumericQuestion.class).setParameter("id", oldId).getSingleResult();
            entityManager.detach(nq);
            nq.setPage(e.getPage());

            logger.debug("Inserting into numeric question");
            entityManager
                .createNativeQuery("INSERT INTO numeric_question(allowed_coefficient_spread, "
                    + "allowed_exponenet_spread, correct_coefficient, correct_exponenet, "
                    + "requires_scale, numeric_question_id) VALUES (?, ?, ?, ?, ?, ?)")
                .setParameter(1, nq.getAllowedCoefficientSpread())
                .setParameter(2, nq.getAllowedCoefficientSpread())
                .setParameter(3, nq.getCorrectCoefficient())
                .setParameter(4, nq.getCorrectExponenet()).setParameter(5, nq.getRequiresScale())
                .setParameter(6, e.getId()).executeUpdate();

            logger.debug("Inserting numeric unit options");
            nq.getOptions().stream().forEach(f -> {
              f.prePersist();
              entityManager
                  .createNativeQuery("INSERT INTO unit_option(unit_option_id, is_correct_option, "
                      + "option_question_id, help_text, human_readable_text) VALUES (?, ?, ?, ?, ?)")
                  .setParameter(1, f.getId()).setParameter(2, f.isCorrectOption())
                  .setParameter(3, e.getId()).setParameter(4, f.getHelpText())
                  .setParameter(5, f.getHumanReadableText()).executeUpdate();
            });

            break;
          default:
            break;
        }
      }
    });

  }

  private List<String> getIds(List<? extends AbstractGeneratedId> entities) {
    return entities.parallelStream().map(e -> e.getId()).collect(Collectors.toList());
  }

  @Override
  public void deleteCourse(String id) {
    entityManager.remove(entityManager.find(Course.class, id));
  }

}
