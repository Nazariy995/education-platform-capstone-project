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

import com.fasterxml.jackson.annotation.JsonView;

import edu.umdearborn.astronomyapp.util.json.View;

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

  @JsonView(View.Student.class)
  private String helpText;

  @JsonView(View.Student.class)
  @NotNull
  @Size(min = 1, max = 10)
  private String humanReadableText;

  @NotNull
  private boolean isActive = true;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public Course getCourseId() {
    return courseId;
  }

  public String getHelpText() {
    return helpText;
  }

  public String getHumanReadableText() {
    return humanReadableText;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  public void setCourseId(Course courseId) {
    this.courseId = courseId;
  }

  public void setHelpText(String helpText) {
    this.helpText = helpText;
  }

  public void setHumanReadableText(String humanReadableText) {
    this.humanReadableText = humanReadableText;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
