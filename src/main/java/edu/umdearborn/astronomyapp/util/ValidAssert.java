package edu.umdearborn.astronomyapp.util;

import org.springframework.validation.Errors;

import edu.umdearborn.astronomyapp.controller.exception.ValidationErrorsException;

public class ValidAssert {

  public static void isValid(Errors errors) {
    if (errors.hasErrors()) {
      throw new ValidationErrorsException(errors);
    }
  }
}
