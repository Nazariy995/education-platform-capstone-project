package edu.umdearborn.astronomyapp.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(indexes = @Index(columnList = "courseId"))
@AttributeOverride(name = "id", column = @Column(name = "moduleId"))
public class Module extends AbstractGeneratedId {

  private static final long serialVersionUID = -8759136244090977612L;

  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "courseId", updatable = false)
  private Course course;

  @NotNull
  private String moduleTitle;

  @NotNull
  @Min(1)
  private int maxStudents = 1;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private String humanReadableText;

  @Temporal(TemporalType.TIMESTAMP)
  private Date openTimestamp;

  @Future
  @Temporal(TemporalType.TIMESTAMP)
  private Date closeTimestamp;

  @Future
  @Temporal(TemporalType.TIMESTAMP)
  private Date dueDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date visibleTimestamp;

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public String getModuleTitle() {
    return moduleTitle;
  }

  public void setModuleTitle(String moduleTitle) {
    this.moduleTitle = moduleTitle;
  }

  public int getMaxStudents() {
    return maxStudents;
  }

  public void setMaxStudents(int maxStudents) {
    this.maxStudents = maxStudents;
  }

  public String getHumanReadableText() {
    return humanReadableText;
  }

  public void setHumanReadableText(String humanReadableText) {
    this.humanReadableText = humanReadableText;
  }

  public Date getOpenTimestamp() {
    return openTimestamp;
  }

  public void setOpenTimestamp(Date openTimestamp) {
    this.openTimestamp = openTimestamp;
  }

  public Date getCloseTimestamp() {
    return closeTimestamp;
  }

  public void setCloseTimestamp(Date closeTimestamp) {
    this.closeTimestamp = closeTimestamp;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public Date getVisibleTimestamp() {
    return visibleTimestamp;
  }

  public void setVisibleTimestamp(Date visibleTimestamp) {
    this.visibleTimestamp = visibleTimestamp;
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
