package edu.umdearborn.astronomyapp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.umdearborn.astronomyapp.entity.Answer;
import edu.umdearborn.astronomyapp.entity.MultipleChoiceQuestion;
import edu.umdearborn.astronomyapp.entity.NumericQuestion;
import edu.umdearborn.astronomyapp.entity.Question;
import edu.umdearborn.astronomyapp.entity.Question.QuestionType;

@Service
@Transactional
public class AutoGradeServiceImpl implements AutoGradeService {

  private static final Logger logger = LoggerFactory.getLogger(AutoGradeServiceImpl.class);

  private EntityManager entityManager;

  public AutoGradeServiceImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Question> getGatekeepers(String moduleId, int pageNum) {
    return entityManager
        .createQuery(
            "select q from Question q join q.page p join p.module m where m.id = :moduleId and "
                + "q.questionType in ('NUMERIC', 'MULTIPLE_CHOICE') and q.isGatekeeper = true "
                + "and (p.order, q.order) in "
                + "(select p.order, max(q.order) from Question q join q.page p join p.module m where "
                + "p.order < :pageNum and m.id = :moduleId group by p.order)",
            Question.class)
        .setParameter("moduleId", moduleId).setParameter("pageNum", pageNum).getResultList();
  }

  @Override
  public boolean checkAnswer(String answerId) {
    Answer answer = entityManager.find(Answer.class, answerId);
    entityManager.clear(); // Clears persistence context so entity manager
                           // will find question with
                           // correct type
    logger.debug("Checking answer: {}", answerId);
    if (QuestionType.NUMERIC.equals(answer.getQuestion().getQuestionType())) {
      return checkNumeric(answer,
          entityManager.find(NumericQuestion.class, answer.getQuestion().getId()));
    } else if (QuestionType.MULTIPLE_CHOICE.equals(answer.getQuestion().getQuestionType())) {
      return checkMultipleChoice(answer,
          entityManager.find(MultipleChoiceQuestion.class, answer.getQuestion().getId()));
    }
    return false;
  }

  protected boolean checkNumeric(Answer answer, NumericQuestion question) {
    String[] parts = Optional.ofNullable(answer.getValue()).orElse("").split("&");
    if (parts.length != 2) {
      logger.debug("Invalid answer val");
      return false;
    }
    BigDecimal numberAnswer = new BigDecimal(parts[0].substring(1));
    BigDecimal upperLimit = question.getCorrectCoefficient()
        .add(question.getAllowedCoefficientSpread()).movePointRight(question.getCorrectExponenet());
    BigDecimal lowerLimit =
        question.getCorrectCoefficient().subtract(question.getAllowedCoefficientSpread())
            .movePointRight(question.getCorrectExponenet());
    if (isWinthin(numberAnswer, upperLimit, lowerLimit)) {
      String correctUnit = question.getOptions().parallelStream().filter(e -> e.isCorrectOption())
          .map(e -> e.getId()).findAny().orElse("!" + parts[1]);

      return parts[1].equals(correctUnit);
    }

    logger.debug("Number value: {} incorrect", numberAnswer);
    return false;
  }

  protected boolean isWinthin(BigDecimal val, BigDecimal... limit) {
    Arrays.sort(limit);
    logger.debug("lower: {} upper: {}", limit[0], limit[1]);
    return val.compareTo(limit[0]) >= 0 && val.compareTo(limit[1]) <= 0;
  }

  protected boolean checkMultipleChoice(Answer answer, MultipleChoiceQuestion question) {
    String correctUnit = question.getOptions().parallelStream().filter(e -> e.isCorrectOption())
        .map(e -> e.getId()).findAny().orElse("!" + answer.getValue());

    return correctUnit.equals(answer.getValue());
  }

  @Override
  public boolean answeredGatekeepers(String moduleId, int pageNum, String groupId) {
    List<Question> qs =
        Optional.of(getGatekeepers(moduleId, pageNum)).orElseGet(ArrayList<Question>::new);
    if (qs.isEmpty()) {
      return true;
    }
    List<Answer> ans = Optional
        .of(entityManager.createQuery("select a from Answer a join a.group g where g.id = :groupId",
            Answer.class).setParameter("groupId", groupId).getResultList())
        .orElseGet(ArrayList<Answer>::new).stream().filter(e -> qs.contains(e.getQuestion()))
        .filter(e -> (e.getPointesEarned() == null
            || e.getPointesEarned().compareTo(BigDecimal.ZERO) == 0))
        .collect(Collectors.toList());

    return ans.isEmpty();


  }

  @Override
  public Answer setPointsEarned(String answerId, BigDecimal points) {
    Answer answer = entityManager.find(Answer.class, answerId);
    answer.setPointesEarned(points);
    entityManager.merge(answer);

    return answer;

  }

}
