package edu.umdearborn.astronomyapp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "unitId"))
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"courseId", "humanReadableText"}),
    indexes = @Index(columnList = "courseId"))
public class Unit extends AbstractGeneratedId {

  private static final long serialVersionUID = 5457895373300173472L;

  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "courseId", updatable = false)
  private Course courseId;

  @NotNull
  @Size(min = 1, max = 10)
  private String humanReadableText;

  private String helpText;

  @NotNull
  private boolean isActive = true;

  public Course getCourseId() {
    return courseId;
  }

  public void setCourseId(Course courseId) {
    this.courseId = courseId;
  }

  public String getHumanReadableText() {
    return humanReadableText;
  }

  public void setHumanReadableText(String humanReadableText) {
    this.humanReadableText = humanReadableText;
  }

  public String getHelpText() {
    return helpText;
  }

  public void setHelpText(String helpText) {
    this.helpText = helpText;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
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
