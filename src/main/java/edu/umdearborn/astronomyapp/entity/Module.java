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
import javax.persistence.PrePersist;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(indexes = @Index(columnList = "courseId"))
@AttributeOverride(name = "id", column = @Column(name = "moduleId"))
public class Module extends AbstractGeneratedId {

  private static final long serialVersionUID = -8759136244090977612L;

  @Future
  @Temporal(TemporalType.TIMESTAMP)
  private Date closeTimestamp;

  @JsonIgnore
  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "courseId", updatable = false)
  private Course course;

  @Future
  @Temporal(TemporalType.TIMESTAMP)
  private Date dueDate;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private String humanReadableText;

  @NotNull
  @Min(1)
  private int maxStudents = 1;

  @NotNull
  private String moduleTitle;

  @Temporal(TemporalType.TIMESTAMP)
  private Date openTimestamp;

  @Temporal(TemporalType.TIMESTAMP)
  private Date visibleTimestamp;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public Date getCloseTimestamp() {
    return closeTimestamp;
  }

  public Course getCourse() {
    return course;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public String getHumanReadableText() {
    return humanReadableText;
  }

  public int getMaxStudents() {
    return maxStudents;
  }

  public String getModuleTitle() {
    return moduleTitle;
  }

  public Date getOpenTimestamp() {
    return openTimestamp;
  }

  public Date getVisibleTimestamp() {
    return visibleTimestamp;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public void setCloseTimestamp(Date closeTimestamp) {
    this.closeTimestamp = closeTimestamp;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public void setHumanReadableText(String humanReadableText) {
    this.humanReadableText = humanReadableText;
  }

  public void setMaxStudents(int maxStudents) {
    this.maxStudents = maxStudents;
  }

  public void setModuleTitle(String moduleTitle) {
    this.moduleTitle = moduleTitle;
  }

  public void setOpenTimestamp(Date openTimestamp) {
    this.openTimestamp = openTimestamp;
  }

  public void setVisibleTimestamp(Date visibleTimestamp) {
    this.visibleTimestamp = visibleTimestamp;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @PrePersist
  public void prePersist() {
    super.prePersist();
    preUpdate();
  }

  public void preUpdate() {
    dueDate = closeTimestamp;
    visibleTimestamp = openTimestamp;
  }

}
