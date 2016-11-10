package edu.umdearborn.astronomyapp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "multipleChoiceId"))
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"humanReadableText", "optionQuestionId"}),
    indexes = @Index(columnList = "optionQuestionId"))
public class MultipleChoiceOption extends AbstractOption {

  private static final long serialVersionUID = -5311013272908483434L;

  @NotNull
  @Size(min = 1)
  private String humanReadableText;

  public String getHumanReadableText() {
    return humanReadableText;
  }

  public void setHumanReadableText(String humanReadableText) {
    this.humanReadableText = humanReadableText;
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
