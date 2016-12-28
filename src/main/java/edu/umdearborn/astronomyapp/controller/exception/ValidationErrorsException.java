package edu.umdearborn.astronomyapp.controller.exception;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;

public class ValidationErrorsException extends RuntimeException {

  private static final long serialVersionUID = 1783170928573468243L;

  private Errors errors;

  public ValidationErrorsException(Errors errors) {
    Assert.notNull(errors, "errors must not be null");
    this.errors = errors;
  }

  public Errors getResult() {
    return errors;
  }

}
