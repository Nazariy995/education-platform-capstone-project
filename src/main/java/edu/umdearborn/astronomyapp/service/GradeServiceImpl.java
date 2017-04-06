package edu.umdearborn.astronomyapp.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import edu.umdearborn.astronomyapp.util.ResultListUtil;

@Service
@Transactional
public class GradeServiceImpl implements GradeService {

  private static final Logger logger = LoggerFactory.getLogger(GradeServiceImpl.class);

  private EntityManager entityManager;

  public GradeServiceImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void exportGrades(String courseId, OutputStream outputStream) {
    List<String> modules = Optional
        .ofNullable(entityManager
            .createQuery("select m.id from Module m join m.course c where c.id = :courseId",
                String.class)
            .setParameter("courseId", courseId).getResultList())
        .orElseGet(ArrayList<String>::new);

    Writer writer = new OutputStreamWriter(outputStream);

    try (CSVPrinter printer =
        new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("email", "module", "grade"))) {

      modules.forEach(e -> {
        Map<String, Object> grades =
            Optional.ofNullable(getGrades(courseId, e)).orElseGet(HashMap<String, Object>::new);

        grades.entrySet().forEach(f -> {
          Map<String, Object> grade = (Map<String, Object>) f.getValue();
          try {
            logger.debug("Appending email: '{}', module: '{}', grade: '{}'", f.getKey(), e,
                grade.get("pointsEarned"));
            printer.printRecord(f.getKey(), e, grade.get("pointsEarned"));
          } catch (IOException ioe) {
            logger.error("Error occured printing record for user: '" + f.getKey()
                + "' for module: '" + e + "'", ioe);
          }
        });
      });

    } catch (IOException ioe) {
      logger.error("Error occured creating grades for course: '" + courseId + "'", ioe);
    } finally {
      IOUtils.closeQuietly(writer);
    }
  }

  @Override
  public Map<String, Object> getGrades(String courseId, String moduleId) {
    List<String> users = getUserEmails(courseId);
    if (users.isEmpty()) {
      return null;
    }
    return users.parallelStream().collect(Collectors.toMap(e -> e, e -> getGrade(e, moduleId)));
  }

  private List<String> getUserEmails(String courseId) {
    return Optional.ofNullable(entityManager
        .createQuery(
            "select u.email from CourseUser cu join cu.user u join cu.course c where c.id = :courseId "
                + "and cu.role = 'STUDENT'",
            String.class)
        .setParameter("courseId", courseId).getResultList()).orElse(new ArrayList<String>());
  }

  @Override
  public Map<String, Object> getGrade(String email, String moduleId) {
    logger.debug("Getting groupdId for module: '{}'", moduleId);
    List<String> results = entityManager.createQuery(
        "select g.id from GroupMember gm join gm.moduleGroup g join g.module m "
            + "join gm.courseUser cu join cu.user u where lower(u.email) = lower(:email)",
        String.class).setParameter("email", email).getResultList();

    if (ResultListUtil.hasResult(results)) {

      logger.debug("Getting submission number for user: '{}' for module: '{}'", email, moduleId);
      long submissionNumber = entityManager.createQuery(
          "select coalesce(max(a.submissionNumber), 0) from Answer a join a.group g where g.id = :groupId",
          Long.class).setParameter("groupId", results.get(0)).getSingleResult();

      if (submissionNumber > 0L) {

        logger.debug("Getting points for user: '{}' for module: '{}'", email, moduleId);
        BigDecimal points = entityManager
            .createQuery(
                "select coalesce(sum(a.pointesEarned), 0) from Answer a join a.group g where g.id = :groupId "
                    + "and a.submissionNumber = :submissionNumber",
                BigDecimal.class)
            .setParameter("submissionNumber", submissionNumber)
            .setParameter("groupId", results.get(0)).getSingleResult();

        logger.debug("Getting submission timestamp for user: '{}' for module: '{}'", email,
            moduleId);
        Date submissionTimestamp = entityManager
            .createQuery(
                "select a.submissionTimestamp from Answer a join a.group g where g.id = :groupId "
                    + "and a.submissionNumber = :submissionNumber",
                Date.class)
            .setParameter("submissionNumber", submissionNumber)
            .setParameter("groupId", results.get(0)).setMaxResults(1).getSingleResult();

        logger.debug("Checking if instructor finished grading for user: '{}' for module: '{}'",
            email, moduleId);
        boolean finishedGrading = entityManager
            .createQuery(
                "select count(a) = 0 from Answer a join a.group g where g.id = :groupId "
                    + "and a.submissionNumber = :submissionNumber and a.pointesEarned is null",
                Boolean.class)
            .setParameter("submissionNumber", submissionNumber)
            .setParameter("groupId", results.get(0)).getSingleResult();

        logger.debug("Retruning grade for user: '{}' for module: '{}'", email, moduleId);

        return ImmutableMap.of("pointsEarned", points, "submissionTimestamp", submissionTimestamp,
            "submissionNumber", submissionNumber, "finishedGrading", finishedGrading);
      }

    }
    logger.debug("User: '{}' has no grade for module: '{}'", email, moduleId);
    return ImmutableMap.of("pointsEarned", BigDecimal.ZERO, "submissionNumber", 0L,
        "finishedGrading", true);
  }

  @Override
  public Map<String, Object> viewStudentGrades(String email, String courseId) {
    return Optional
        .ofNullable(entityManager
            .createQuery("select m.id from Module m join m.course c where c.id = :courseId",
                String.class)
            .setParameter("courseId", courseId).getResultList())
        .orElseGet(ArrayList<String>::new).stream()
        .collect(Collectors.toMap(e -> e, e -> getGrade(email, e)));
  }
}
