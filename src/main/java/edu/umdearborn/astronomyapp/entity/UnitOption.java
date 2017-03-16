package edu.umdearborn.astronomyapp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import edu.umdearborn.astronomyapp.util.json.View;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "unitOptionId"))
@Table(indexes = @Index(columnList = "optionQuestionId"))
public class UnitOption extends AbstractOption {

  private static final long serialVersionUID = -2298005560218235570L;

  @JsonView(View.Student.class)
  private String helpText;

  @JsonView(View.Student.class)
  @NotNull
  @Size(min = 1, max = 10)
  private String humanReadableText;

  public String getHelpText() {
    return helpText;
  }

  public void setHelpText(String helpText) {
    this.helpText = helpText;
  }

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
