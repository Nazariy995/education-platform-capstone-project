package edu.umdearborn.astronomyapp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "gradeId"))
public class Grade extends AbstractGeneratedId {

  private static final long serialVersionUID = -6636738045828148302L;
  
  @NotNull
  @ManyToOne
  @JoinColumn(name = "courseUserId")
  private CourseUser user;
  
  @NotNull
  @ManyToOne
  @JoinColumn(name = "moduleId")
  private Module module;
  
  @NotNull
  private int totalPointsEarned = 0;

  private String comment;

  public CourseUser getUser() {
    return user;
  }

  public void setUser(CourseUser user) {
    this.user = user;
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  public int getTotalPointsEarned() {
    return totalPointsEarned;
  }

  public void setTotalPointsEarned(int totalPointsEarned) {
    this.totalPointsEarned = totalPointsEarned;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
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
