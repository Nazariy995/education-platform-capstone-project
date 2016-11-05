package edu.umdearborn.astronomyapp.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class Course {
  
  @Id
  private String courseId;
  
  private String subjectCode;
  
  private String courseCode;
  
  @Column(nullable = false)
  private String courseTitle;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date openTimestamp;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date closeTimestamp;

  
  public String getCourseId() {
    return courseId;
  }

  
  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  
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
  
  @PrePersist
  protected void prePersist() {
    courseId = UUID.randomUUID().toString();
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
