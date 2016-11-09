package edu.umdearborn.astronomyapp.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "answerId"))
public class Answer extends AbstractGeneratedId {

  private static final long serialVersionUID = 7968210698660722474L;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "moduleGroupId")
  private ModuleGroup group;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "questionId")
  private Question quiestion;

  @NotNull
  private int pointesEarned = 0;

  @NotNull
  private int submissionNumber = 0;

  private String value;

  private String comment;

  @Temporal(TemporalType.TIMESTAMP)
  private Date submissionTimestamp;

  public ModuleGroup getGroup() {
    return group;
  }

  public void setGroup(ModuleGroup group) {
    this.group = group;
  }

  public Question getQuiestion() {
    return quiestion;
  }

  public void setQuiestion(Question quiestion) {
    this.quiestion = quiestion;
  }

  public int getPointesEarned() {
    return pointesEarned;
  }

  public void setPointesEarned(int pointesEarned) {
    this.pointesEarned = pointesEarned;
  }

  public int getSubmissionNumber() {
    return submissionNumber;
  }

  public void setSubmissionNumber(int submissionNumber) {
    this.submissionNumber = submissionNumber;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Date getSubmissionTimestamp() {
    return submissionTimestamp;
  }

  public void setSubmissionTimestamp(Date submissionTimestamp) {
    this.submissionTimestamp = submissionTimestamp;
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
