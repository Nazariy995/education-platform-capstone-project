package edu.umdearborn.astronomyapp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "courseUserId"))
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "courseId"}),
    indexes = @Index(columnList = "userId"))
public class CourseUser extends AbstractGeneratedId {

  public static enum CourseRole {
    INSTRUCTOR, STUDENT, TA;
  }

  private static final long serialVersionUID = 453757782768837852L;

  @JsonIgnore
  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "courseId", updatable = false)
  private Course course;

  @JsonIgnore
  @NotNull
  private boolean isActive = true;

  @JsonProperty("courseRole")
  @NotNull
  @Enumerated(EnumType.STRING)
  private CourseRole role;

  @JsonIgnoreProperties("appRoles")
  @JsonUnwrapped
  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "userId", updatable = false)
  private AstroAppUser user;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public Course getCourse() {
    return course;
  }

  public CourseRole getRole() {
    return role;
  }

  public AstroAppUser getUser() {
    return user;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @JsonIgnore
  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public void setRole(CourseRole role) {
    this.role = role;
  }

  public void setUser(AstroAppUser user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
