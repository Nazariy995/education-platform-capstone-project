package edu.umdearborn.astronomyapp.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.umdearborn.astronomyapp.entity.Course;
import edu.umdearborn.astronomyapp.entity.CourseUser;
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
  public Course createCourse(Course course) {
    entityManager.persist(course);
    return course;
  }

  @Override
  public Course updateCourse(Course course) {
    entityManager.merge(course);
    return course;
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


}
