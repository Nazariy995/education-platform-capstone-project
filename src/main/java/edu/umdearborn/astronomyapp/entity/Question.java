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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import edu.umdearborn.astronomyapp.util.json.View;

@Entity
@DiscriminatorValue("QUESTION")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "questionType")
@PrimaryKeyJoinColumn(name = "QUESTION_ID")
public class Question extends PageItem {

  public static enum QuestionType {
    FREE_RESPONSE(false), IMAGE(false), MULTIPLE_CHOICE(true), NUMERIC(true);

    @JsonView(View.Student.class)
    private boolean isMachineGradeable;

    QuestionType(boolean isMachineGradeable) {
      this.isMachineGradeable = isMachineGradeable;
    }

    public boolean isMachineGradeable() {
      return isMachineGradeable;
    }
  }

  private static final long serialVersionUID = -3801803453986972162L;

  private String defaultComment;

  @JsonView(View.Student.class)
  @NotNull
  private boolean isGatekeeper = false;

  @JsonView(View.Student.class)
  @NotNull
  @DecimalMin("0")
  @Column(precision = 8, scale = 4)
  private BigDecimal points = new BigDecimal(0);

  @JsonView(View.Student.class)
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private QuestionType questionType;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public String getDefaultComment() {
    return defaultComment;
  }

  public BigDecimal getPoints() {
    return points;
  }

  public QuestionType getQuestionType() {
    return questionType;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public boolean isGatekeeper() {
    return isGatekeeper;
  }

  public boolean isMachineGradeable() {
    return questionType.isMachineGradeable;
  }

  public void setDefaultComment(String defaultComment) {
    this.defaultComment = defaultComment;
  }

  public void setGatekeeper(boolean isGatekeeper) {
    this.isGatekeeper = isGatekeeper;
  }

  public void setPoints(BigDecimal points) {
    this.points = points;
  }

  public void setQuestionType(QuestionType questionType) {
    this.questionType = questionType;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
