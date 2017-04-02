package edu.umdearborn.astronomyapp.entity.json;

import java.io.IOException;

import org.junit.Test;

import com.google.gson.Gson;

import edu.umdearborn.astronomyapp.entity.MultipleChoiceQuestion;

public class MultipleChoiceJsonTest {

  @Test
  public void deseralizeTest() throws IOException {
    String json =
        "{\"questionType\":\"MULTIPLE_CHOICE\",\"pageItemType\":\"QUESTION\",\"isGatekeeper\":false,\"options\":[{\"humanReadableText\":\"AFASDFASDF\",\"isCorrectOption\":false},{\"humanReadableText\":\"ASDFASDF\",\"isCorrectOption\":true}],\"humanReadableText\":\"QUESTION 1\",\"points\":5}";
    System.out.println(json);

    MultipleChoiceQuestion question = new Gson().fromJson(json, MultipleChoiceQuestion.class);

    System.out.println(question);
  }
}
