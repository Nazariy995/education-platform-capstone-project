package edu.umdearborn.astronomyapp.entity.json;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.json.JacksonTester;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.umdearborn.astronomyapp.entity.Question;

public class QuestionJsonTest extends JsonTestHelper<Question> {

  private static final Logger logger = LoggerFactory.getLogger(QuestionJsonTest.class);

  private JacksonTester<Question> json;

  @Before
  public void before() {
    JacksonTester.initFields(this, new ObjectMapper());
  }

  @Test
  public void deseralizeTest() throws IOException {
    // @formatter:off
    Question question = json.parseObject("{      \"questionType\":\"FREE_RESPONSE\",    \"pageItemType\":\"QUESTION\",    \"humanReadableText\":\"This is a test question\",    \"points\":5 }");
    // @formatter:on
    logger.info("Deseralized:\n-------------\n{}\n-------------", question.toString());

  }
}
