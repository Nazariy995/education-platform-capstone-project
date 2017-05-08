package edu.umdearborn.astronomyapp.validation.validator;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import edu.umdearborn.astronomyapp.entity.NumericQuestion;

public class NumericQuestionValidator extends QuestionValidator {

  private static final Logger logger = LoggerFactory.getLogger(NumericQuestionValidator.class);

  @Override
  public boolean supports(Class<?> clazz) {
    return NumericQuestion.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    super.validate(target, errors);
    NumericQuestion cast = (NumericQuestion) target;
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "options", "invalid");
    if (cast.getRequiresScale() < 0 || cast.getRequiresScale() > 10) {
      errors.rejectValue("requiresScale", "invalid", "Required scale must be between 0 - 10");
    }
    if (cast.getOptions().isEmpty()) {
      errors.rejectValue("options", "invalid", "You must give options");
    }
    logger.debug("Number of correct options: {}", cast.getOptions().stream()
        .filter(e -> e.isCorrectOption()).peek(e -> logger.debug("{}", e)).count());
    if (cast.getOptions().stream().filter(e -> e.isCorrectOption()).count() != 1L) {
      errors.rejectValue("options", "invalid", "One and only one option must be true");
    }

    if (errors.hasErrors()) {
      logger.debug("Errors: ", Arrays.toString(
          errors.getAllErrors().parallelStream().map(e -> e.getDefaultMessage()).toArray()));
    }
  }
}
