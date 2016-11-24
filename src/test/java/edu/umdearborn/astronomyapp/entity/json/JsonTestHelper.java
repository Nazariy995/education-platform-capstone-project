package edu.umdearborn.astronomyapp.entity.json;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
@PropertySource("classpath:json-test.properties")
public abstract class JsonTestHelper<T> {
  
  private static final Logger logger = LoggerFactory.getLogger(JsonTestHelper.class);
  
  @Autowired
  protected JacksonTester<T> json;
  
  protected JsonContent<T> writeSafely(T value) {
    try {
      return json.write(value);
    } catch (IOException ioe) {
      logger.error("Error writing json", ioe);
      throw new JsonTestException(ioe);
    }
  }

}
