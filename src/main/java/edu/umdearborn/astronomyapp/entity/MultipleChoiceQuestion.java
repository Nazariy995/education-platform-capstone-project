package edu.umdearborn.astronomyapp.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@DiscriminatorValue("MULTIPLE_CHOICE")
@PrimaryKeyJoinColumn(name = "MULTIPLE_CHOICE_QUESTION_ID")
public class MultipleChoiceQuestion extends AbstractOptionsQuestion<MultipleChoiceOption> {

  private static final long serialVersionUID = -936973458962467475L;

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
