package edu.umdearborn.astronomyapp.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import edu.umdearborn.astronomyapp.entity.Answer;
import edu.umdearborn.astronomyapp.entity.MultipleChoiceOption;
import edu.umdearborn.astronomyapp.entity.MultipleChoiceQuestion;
import edu.umdearborn.astronomyapp.entity.NumericQuestion;
import edu.umdearborn.astronomyapp.entity.UnitOption;

public class AutoGradeServiceImplTest extends AutoGradeServiceImpl {

  public AutoGradeServiceImplTest() {
    super(null);
  }

  private NumericQuestion        numericQuestion;
  private MultipleChoiceQuestion multipleChoiceQuestion;

  @Before
  public void before() {
    numericQuestion = new NumericQuestion();
    numericQuestion.setAllowedCoefficientSpread(new BigDecimal("0.03"));
    numericQuestion.setAllowedExponenetSpread(0);
    numericQuestion.setCorrectCoefficient(new BigDecimal("4.49"));
    UnitOption unitOption1 = new UnitOption();
    unitOption1.setId("id1");
    unitOption1.setCorrectOption(false);
    UnitOption unitOption2 = new UnitOption();
    unitOption2.setId("id2");
    unitOption2.setCorrectOption(true);
    UnitOption unitOption3 = new UnitOption();
    unitOption3.setId("id3");
    unitOption3.setCorrectOption(false);
    UnitOption unitOption4 = new UnitOption();
    unitOption4.setId("id4");
    unitOption4.setCorrectOption(false);
    Set<UnitOption> unitOptions = new HashSet<>();
    unitOptions.add(unitOption1);
    unitOptions.add(unitOption2);
    unitOptions.add(unitOption3);
    unitOptions.add(unitOption4);
    numericQuestion.setOptions(unitOptions);

    multipleChoiceQuestion = new MultipleChoiceQuestion();
    MultipleChoiceOption multipleChoiceOption1 = new MultipleChoiceOption();
    multipleChoiceOption1.setId("id1");
    multipleChoiceOption1.setCorrectOption(false);
    MultipleChoiceOption multipleChoiceOption2 = new MultipleChoiceOption();
    multipleChoiceOption2.setId("id2");
    multipleChoiceOption2.setCorrectOption(true);
    MultipleChoiceOption multipleChoiceOption3 = new MultipleChoiceOption();
    multipleChoiceOption3.setId("id3");
    multipleChoiceOption3.setCorrectOption(false);
    MultipleChoiceOption multipleChoiceOption4 = new MultipleChoiceOption();
    multipleChoiceOption4.setId("id4");
    multipleChoiceOption4.setCorrectOption(false);
    Set<MultipleChoiceOption> multipleChoiceOptions = new HashSet<>();
    multipleChoiceOptions.add(multipleChoiceOption1);
    multipleChoiceOptions.add(multipleChoiceOption2);
    multipleChoiceOptions.add(multipleChoiceOption3);
    multipleChoiceOptions.add(multipleChoiceOption4);
    multipleChoiceQuestion.setOptions(multipleChoiceOptions);
  }

  @Test
  public void checkNumericTestNegative() {
    Answer answer = new Answer();
    answer.setValue("#4.51e-7&id2");

    numericQuestion.setCorrectExponenet(-7);

    MatcherAssert.assertThat("Numbers did not match", super.checkNumeric(answer, numericQuestion));

    answer.setValue("#4.521e-7&id2");
    MatcherAssert.assertThat("Numbers did match", !super.checkNumeric(answer, numericQuestion));

    answer.setValue("#4.51e-7&id1");
    MatcherAssert.assertThat("Numbers did match", !super.checkNumeric(answer, numericQuestion));
  }

  @Test
  public void checkNumericTestPosititave() {
    Answer answer = new Answer();
    answer.setValue("#4.51e7&id2");

    numericQuestion.setCorrectExponenet(7);
    MatcherAssert.assertThat("Numbers did not match", super.checkNumeric(answer, numericQuestion));

    answer.setValue("#4.521e7&id2");
    MatcherAssert.assertThat("Numbers did match", !super.checkNumeric(answer, numericQuestion));

    answer.setValue("#4.51e7&id1");
    MatcherAssert.assertThat("Numbers did match", !super.checkNumeric(answer, numericQuestion));
  }

  @Test
  public void checkNumericEdgeCaseTest() {
    Answer answer = new Answer();

    MatcherAssert.assertThat("Numbers did match", !super.checkNumeric(answer, numericQuestion));
  }

  @Test
  public void checkMultipleChoiceTest() {
    Answer answer = new Answer();
    answer.setValue("id2");

    MatcherAssert.assertThat("Numbers did not match",
        super.checkMultipleChoice(answer, multipleChoiceQuestion));

    answer.setValue("id1");
    MatcherAssert.assertThat("Numbers did match",
        !super.checkMultipleChoice(answer, multipleChoiceQuestion));

  }
}
