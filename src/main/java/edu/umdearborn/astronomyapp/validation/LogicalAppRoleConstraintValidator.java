package edu.umdearborn.astronomyapp.validation;

import java.util.Arrays;
import java.util.Set;

import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections.CollectionUtils;

import edu.umdearborn.astronomyapp.entity.AstroAppUser;
import edu.umdearborn.astronomyapp.validation.annotation.LogicalAppRoles;

public class LogicalAppRoleConstraintValidator
    extends AbstractConstraintValidator<LogicalAppRoles, Set<AstroAppUser.Role>> {

  @Override
  public boolean isValid(Set<AstroAppUser.Role> value, ConstraintValidatorContext context) {

    if (value.contains(AstroAppUser.Role.USER)) {
      return !(CollectionUtils.containsAny(value,
          Arrays.asList(AstroAppUser.Role.ADMIN, AstroAppUser.Role.INSTRUCTOR)));
    }

    return true;
  }

}
