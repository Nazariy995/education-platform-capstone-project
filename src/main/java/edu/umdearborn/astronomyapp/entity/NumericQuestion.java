package edu.umdearborn.astronomyapp.entity;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@DiscriminatorValue("NUMERIC")
@AttributeOverride(name = "optionQuestionId", column = @Column(name = "numericQuestionOptionId"))
public class NumericQuestion extends AbstractOptionsQuestion<UnitOption> {

  private static final long serialVersionUID = -4122369698440997963L;

  // private static final BigDecimal BASE = new BigDecimal(10);

  // private static final String DECIMAL_FORMAT = "0.0E0";

  @Min(0)
  @Max(10)
  @NotNull
  private int requiresScale = 0;

  @NotNull
  @Column(precision = 11, scale = 10)
  private BigDecimal correctCoefficient = new BigDecimal(0);

  @NotNull
  @Min(0)
  @Max(10)
  private int correctExponenet = 0;

  @NotNull
  @Column(precision = 11, scale = 10)
  private BigDecimal allowedCoefficientSpread = new BigDecimal(0);

  @NotNull
  @Min(0)
  @Max(10)
  private int allowedExponenetSpread = 0;

  public int getRequiresScale() {
    return requiresScale;
  }

  public void setRequiresScale(int requiresScale) {
    this.requiresScale = requiresScale;
  }

  public BigDecimal getCorrectCoefficient() {
    return correctCoefficient;
  }

  public void setCorrectCoefficient(BigDecimal correctCoefficient) {
    this.correctCoefficient = correctCoefficient;
  }

  public int getCorrectExponenet() {
    return correctExponenet;
  }

  public void setCorrectExponenet(int correctExponenet) {
    this.correctExponenet = correctExponenet;
  }

  // TODO: (Patrick) move this logic to a grading service
  // @Override
  // public String getAnswer() {
  // BigDecimal multiplicand = BASE.pow(correctExponenet);
  // BigDecimal answer = new BigDecimal(correctCoefficient).multiply(multiplicand);
  // return new StringBuilder(formatNumber(answer)).append(" ").append(getCorrectUnit()).toString();
  // }
  //
  // private String formatNumber(BigDecimal aswer) {
  // NumberFormat format = new DecimalFormat(DECIMAL_FORMAT);
  // format.setRoundingMode(RoundingMode.HALF_UP);
  // format.setMaximumFractionDigits(requiresScale);
  // return format.format(aswer);
  // }
  //
  // private String getCorrectUnit() {
  // return options.parallelStream().filter(x -> x.isCorrect())
  // .map(x -> x.getUnit().getHumanReadableText()).findFirst().orElse("");
  // }

  public BigDecimal getAllowedCoefficientSpread() {
    return allowedCoefficientSpread;
  }

  public void setAllowedCoefficientSpread(BigDecimal allowedCoefficientSpread) {
    this.allowedCoefficientSpread = allowedCoefficientSpread;
  }

  public int getAllowedExponenetSpread() {
    return allowedExponenetSpread;
  }

  public void setAllowedExponenetSpread(int allowedExponenetSpread) {
    this.allowedExponenetSpread = allowedExponenetSpread;
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
