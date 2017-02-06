package edu.umdearborn.astronomyapp.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "courseId"))
@Table(indexes = {@Index(columnList = "subjectCode"), @Index(columnList = "courseCode")})
public class Course extends AbstractGeneratedId {

  private static final long serialVersionUID = 2442282448974230234L;

  @NotNull
  @Future
  @Temporal(TemporalType.TIMESTAMP)
  private Date closeTimestamp;

  @NotNull
  @Column(updatable = false)
  @Size(min = 1, max = 6)
  private String courseCode;

  @NotNull
  @Size(min = 1, max = 60)
  private String courseTitle;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private Date openTimestamp;

  @NotNull
  @Column(updatable = false)
  @Size(min = 1, max = 6)
  private String subjectCode;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public Date getCloseTimestamp() {
    return closeTimestamp;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public Date getOpenTimestamp() {
    return openTimestamp;
  }

  public String getSubjectCode() {
    return subjectCode;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public void setCloseTimestamp(Date closeTimestamp) {
    this.closeTimestamp = closeTimestamp;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public void setOpenTimestamp(Date openTimestamp) {
    this.openTimestamp = openTimestamp;
  }

  public void setSubjectCode(String subjectCode) {
    this.subjectCode = subjectCode;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
