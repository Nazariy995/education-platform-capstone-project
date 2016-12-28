package edu.umdearborn.astronomyapp.validation.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.umdearborn.astronomyapp.validation.LogicalAppRoleConstraintValidator;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@NotNull
@Size(min = 1, max = 2)
@Constraint(validatedBy = LogicalAppRoleConstraintValidator.class)
public @interface LogicalAppRoles {

  public abstract Class<?>[] groups() default {};

  public abstract String message() default "{edu.umdearborn.astronomyapp.util.validator.Role.message}";

  public abstract Class<? extends Payload>[] payload() default {};

}
