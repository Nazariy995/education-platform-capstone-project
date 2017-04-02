package edu.umdearborn.astronomyapp.validation.validator;

import java.math.BigDecimal;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import edu.umdearborn.astronomyapp.entity.Question;

public class QuestionValidator extends PageItemValidator {

  private static final Logger logger = LoggerFactory.getLogger(QuestionValidator.class);

  @Override
  public boolean supports(Class<?> clazz) {
    return Question.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    super.validate(target, errors);
    Question cast = (Question) target;
    if (cast.getPoints().compareTo(BigDecimal.ZERO) == -1) {
      logger.debug("Points is less than 0");
      errors.rejectValue("points", "invalid", "points must not be less than 0");
    }
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "questionType", "invalid");
    if (errors.hasErrors()) {
      logger.debug("Errors: ", Arrays.toString(
          errors.getAllErrors().parallelStream().map(e -> e.getDefaultMessage()).toArray()));
    }
  }

}
