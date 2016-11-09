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
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "courseUserId"))
@Table(indexes = @Index(columnList = "courseId"))
public class CourseUser extends AbstractGeneratedId {

  private static final long serialVersionUID = 453757782768837852L;

  public static enum CourseRole {
    STUDENT, TA, INSTRUCTOR, NUL;
  }

  @NotNull
  @ManyToOne
  @JoinColumn(name = "email")
  private AstroAppUser user;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "courseId")
  private Course course;

  @NotNull
  @Enumerated(EnumType.STRING)
  private CourseRole role = CourseRole.NUL;

  public AstroAppUser getUser() {
    return user;
  }

  public void setUser(AstroAppUser user) {
    this.user = user;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public CourseRole getRole() {
    return role;
  }

  public void setRole(CourseRole role) {
    this.role = role;
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
