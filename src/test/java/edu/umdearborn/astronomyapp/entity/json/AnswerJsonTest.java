package edu.umdearborn.astronomyapp.entity.json;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.json.JsonContent;

import edu.umdearborn.astronomyapp.entity.Answer;
import edu.umdearborn.astronomyapp.entity.Question;

public class AnswerJsonTest extends JsonTestHelper<Answer> {

  private static final Logger logger = LoggerFactory.getLogger(AnswerJsonTest.class);

  private Question question = new Question();
  private Answer   answer   = new Answer();

  @Before
  public void before() {
    question.setId("Question ID");
    answer.setQuestion(question);
  }

  @Test
  public void testNumeric() {
    answer.setValue("#3.5&unit-id");
    JsonContent<?> json = super.writeSafely(answer);
    logger.info("Json string:\n{}", json.getJson());

    answer.setValue("#3.5e59&unit-id");
    json = super.writeSafely(answer);
    logger.info("Json string:\n{}", json.getJson());
  }

  @Test
  public void testNonNumeric() {
    answer.setValue("some answer....");
    JsonContent<?> json = super.writeSafely(answer);
    logger.info("Json string:\n{}", json.getJson());

    answer.setValue("t#3.5e59&unit-id");
    json = super.writeSafely(answer);
    logger.info("Json string:\n{}", json.getJson());
  }
}
