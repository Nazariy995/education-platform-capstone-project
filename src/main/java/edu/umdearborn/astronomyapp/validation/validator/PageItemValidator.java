package edu.umdearborn.astronomyapp.validation.validator;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.umdearborn.astronomyapp.entity.PageItem;

public class PageItemValidator implements Validator {

  private static final Logger logger = LoggerFactory.getLogger(PageItemValidator.class);

  @Override
  public boolean supports(Class<?> clazz) {
    return PageItem.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "humanReadableText", "required");
    ValidationUtils.rejectIfEmpty(errors, "pageItemType", "required");

    if (errors.hasErrors()) {
      logger.debug("Errors: ", Arrays.toString(
          errors.getAllErrors().parallelStream().map(e -> e.getDefaultMessage()).toArray()));
    }

  }

}
