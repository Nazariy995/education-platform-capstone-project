package edu.umdearborn.astronomyapp.entity;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@MappedSuperclass
public abstract class AbstractOption extends AbstractGeneratedId {

  private static final long serialVersionUID = -8982150102570938109L;

  @NotNull
  private boolean isCorrectOption = false;

  public boolean isCorrectOption() {
    return isCorrectOption;
  }

  public void setCorrectOption(boolean isCorrectOption) {
    this.isCorrectOption = isCorrectOption;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
