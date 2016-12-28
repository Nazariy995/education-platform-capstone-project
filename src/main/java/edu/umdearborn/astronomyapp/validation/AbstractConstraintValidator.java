package edu.umdearborn.astronomyapp.validation;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;

public abstract class AbstractConstraintValidator<A extends Annotation, T>
    implements ConstraintValidator<A, T> {

  @Override
  public void initialize(A constraintAnnotation) {
    // Intentionally left blank
  }
}
