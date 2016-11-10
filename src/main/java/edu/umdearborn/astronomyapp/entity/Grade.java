package edu.umdearborn.astronomyapp.entity;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "gradeId"))
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"courseUserId", "moduleId"}),
    indexes = @Index(columnList = "courseUserId"))
public class Grade extends AbstractGeneratedId {

  private static final long serialVersionUID = -6636738045828148302L;

  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "courseUserId", updatable = false)
  private CourseUser user;

  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "moduleId", updatable = false)
  private Module module;

  @NotNull
  @DecimalMin("0")
  @Column(precision = 8, scale = 4)
  private BigDecimal totalPointsEarned = new BigDecimal(0);

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

  public BigDecimal getTotalPointsEarned() {
    return totalPointsEarned;
  }

  public void setTotalPointsEarned(BigDecimal totalPointsEarned) {
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