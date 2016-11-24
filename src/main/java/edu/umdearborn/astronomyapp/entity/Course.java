package edu.umdearborn.astronomyapp.entity;

import static edu.umdearborn.astronomyapp.util.constants.CommonConstants.ISO_DATE_FORMAT;

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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "courseId"))
@Table(indexes = {@Index(columnList = "subjectCode"), @Index(columnList = "courseCode")})
public class Course extends AbstractGeneratedId {

  private static final long serialVersionUID = 2442282448974230234L;

  @NotNull
  @Column(updatable = false)
  @Size(min = 1, max = 6)
  private String subjectCode;

  @NotNull
  @Column(updatable = false)
  @Size(min = 1, max = 6)
  private String courseCode;

  @NotNull
  @Size(min = 1, max = 60)
  private String courseTitle;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = ISO_DATE_FORMAT, locale = "UTC")
  private Date openTimestamp;

  @Future
  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = ISO_DATE_FORMAT, locale = "UTC")
  private Date closeTimestamp;

  public String getSubjectCode() {
    return subjectCode;
  }

  public void setSubjectCode(String subjectCode) {
    this.subjectCode = subjectCode;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
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
