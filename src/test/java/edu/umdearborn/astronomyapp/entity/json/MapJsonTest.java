package edu.umdearborn.astronomyapp.entity.json;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
@PropertySource("classpath:json-test.properties")
public class MapJsonTest {

  private static final Logger logger = LoggerFactory.getLogger(MapJsonTest.class);
  
  @Autowired
  protected JacksonTester<Map<String, Map<String, String>>> saveAnswerJson;
  
  @Test
  public void saveAnserDeseralizeTest() throws IOException {
    String answersJson = "{  \r\n" + 
        "    \"item1-page2-mod2-course1A\":{  \r\n" + 
        "        \"type\":\"NUMERIC\",\r\n" + 
        "        \"answer\":12.3,\r\n" + 
        "        \"unit\":\"unit2-course1A\"\r\n" + 
        "    },\r\n" + 
        "    \"item3-page2-mod2-course1A\":{  \r\n" + 
        "        \"type\":\"FREE_RESPONSE\",\r\n" + 
        "        \"answer\":\"asdfasdfasdfasdfasdf\\nsdfgsdfgsdfg\\nsdfgsdfg\"\r\n" + 
        "    }\r\n" + 
        "}\r\n" + 
        "\r\n" + 
        "{  \r\n" + 
        "    \"item4-page1-mod1-course1A\":{  \r\n" + 
        "        \"type\":\"NUMERIC\"\r\n" + 
        "    },\r\n" + 
        "    \"item1-page1-mod1-course1A\":{  \r\n" + 
        "        \"type\":\"MULTIPLE_CHOICE\",\r\n" + 
        "        \"answer\":\"choice4-item1-page1-mod1-course1A\"\r\n" + 
        "    },\r\n" + 
        "    \"item2-page1-mod1-course1A\":{  \r\n" + 
        "        \"type\":\"MULTIPLE_CHOICE\",\r\n" + 
        "        \"answer\":\"choice1-item2-page1-mod1-course1A\"\r\n" + 
        "    },\r\n" + 
        "    \"item3-page1-mod1-course1A\":{  \r\n" + 
        "        \"type\":\"MULTIPLE_CHOICE\"\r\n" + 
        "    }\r\n" + 
        "}";
    
    Map<String, Map<String, String>> answers = saveAnswerJson.parseObject(answersJson);
    
    logger.info(Arrays.toString(answers.entrySet().toArray()));
  }
}
