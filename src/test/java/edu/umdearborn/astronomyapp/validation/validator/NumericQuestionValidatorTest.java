package edu.umdearborn.astronomyapp.validation.validator;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import edu.umdearborn.astronomyapp.entity.NumericQuestion;
import edu.umdearborn.astronomyapp.entity.PageItem.PageItemType;
import edu.umdearborn.astronomyapp.entity.Question.QuestionType;
import edu.umdearborn.astronomyapp.entity.UnitOption;

public class NumericQuestionValidatorTest {

  private NumericQuestionValidator validator = new NumericQuestionValidator();
  private Errors                   errors;
  NumericQuestion                  question;

  @Before
  public void before() {
    question = new NumericQuestion();
    question.setHumanReadableText("text");
    question.setPageItemType(PageItemType.QUESTION);
    question.setQuestionType(QuestionType.NUMERIC);
    errors = new BeanPropertyBindingResult(question, "numericQuestion");
  }

  @Test
  public void pass() {
    HashSet<UnitOption> options = new HashSet<>();
    UnitOption o1 = new UnitOption();
    o1.setCorrectOption(true);
    o1.setHelpText("o1");
    options.add(o1);
    UnitOption o2 = new UnitOption();
    o2.setHelpText("o2");
    options.add(o2);
    UnitOption o3 = new UnitOption();
    o3.setHelpText("o3");
    options.add(o3);
    UnitOption o4 = new UnitOption();
    o4.setHelpText("o4");
    options.add(o4);
    question.setOptions(options);
    question.getOptions().stream().peek(e -> System.out.println(e)).filter(e -> e.isCorrectOption())
        .peek(e -> System.out.println(e)).count();
    validator.validate(question, errors);
    assertThat("Has errors: ", !errors.hasErrors());
  }

  @Test
  public void fail() {
    HashSet<UnitOption> options = new HashSet<>();
    UnitOption o1 = new UnitOption();
    o1.setHelpText("o1");
    o1.setCorrectOption(true);
    options.add(o1);
    UnitOption o2 = new UnitOption();
    o2.setHelpText("o2");
    options.add(o2);
    UnitOption o3 = new UnitOption();
    o3.setHelpText("03");
    options.add(o3);
    o3.setCorrectOption(true);
    UnitOption o4 = new UnitOption();
    o4.setHelpText("04");
    options.add(o4);

    System.out.println(options.size());
    options.stream().forEach(e -> {
      System.out.println(e.isCorrectOption());
    });

    question.setOptions(options);
    validator.validate(question, errors);
    assertThat("Has no errors: ", errors.hasErrors());

    o1.setCorrectOption(false);
    o2.setCorrectOption(false);
    validator.validate(question, errors);
    assertThat("Has no errors: ", errors.hasErrors());
  }
}
