package edu.umdearborn.astronomyapp.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@DiscriminatorValue("QUESTION")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "questionType")
public class Question extends PageItem {

  private static final long serialVersionUID = -3801803453986972162L;

  public static enum QuestionType {
    NUMERIC(true), MULTIPLE_CHOICE(true), IMAGE(false), FREE_RESPONSE(false), NUL(false);

    private boolean isMachineGradeable;

    QuestionType(boolean isMachineGradeable) {
      this.isMachineGradeable = isMachineGradeable;
    }

    public boolean isMachineGradeable() {
      return isMachineGradeable;
    }
  }

  @NotNull
  @DecimalMin("0")
  @Column(precision = 8, scale = 4)
  private BigDecimal points = new BigDecimal(0);

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(length = 15)
  private QuestionType questionType = QuestionType.NUL;

  @NotNull
  private boolean isGatekeeper = false;

  private String defaultComment;

  public BigDecimal getPoints() {
    return points;
  }

  public void setPoints(BigDecimal points) {
    this.points = points;
  }

  public QuestionType getQuestionType() {
    return questionType;
  }

  public void setQuestionType(QuestionType questionType) {
    this.questionType = questionType;
  }

  public boolean isGatekeeper() {
    return isGatekeeper;
  }

  public void setGatekeeper(boolean isGatekeeper) {
    this.isGatekeeper = isGatekeeper;
  }

  public String getDefaultComment() {
    return defaultComment;
  }

  public void setDefaultComment(String defaultComment) {
    this.defaultComment = defaultComment;
  }

  public boolean isMachineGradeable() {
    return questionType.isMachineGradeable;
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
