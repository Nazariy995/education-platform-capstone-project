package edu.umdearborn.astronomyapp.validation;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;

public abstract class ValidationTestHelper {

  protected Validator validator;

  @Before
  public void before() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

}
