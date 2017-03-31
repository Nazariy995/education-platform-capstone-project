package edu.umdearborn.astronomyapp.validation.validator;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import edu.umdearborn.astronomyapp.entity.MultipleChoiceQuestion;

public class MultipleChoiceQuestionValidator extends QuestionValidator {

  private static final Logger logger =
      LoggerFactory.getLogger(MultipleChoiceQuestionValidator.class);

  @Override
  public boolean supports(Class<?> clazz) {
    return MultipleChoiceQuestion.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    super.validate(target, errors);
    MultipleChoiceQuestion cast = (MultipleChoiceQuestion) target;
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "options", "invalid");
    if (cast.getOptions().isEmpty()) {
      errors.rejectValue("options", "invalid", "You must give options");
    }
    if (cast.getOptions().stream().filter(e -> e.isCorrectOption()).count() != 1L) {
      errors.rejectValue("options", "invalid", "One and only one option must be true");
    }

    if (errors.hasErrors()) {
      logger.debug("Errors: ", Arrays.toString(
          errors.getAllErrors().parallelStream().map(e -> e.getDefaultMessage()).toArray()));
    }
  }
}
