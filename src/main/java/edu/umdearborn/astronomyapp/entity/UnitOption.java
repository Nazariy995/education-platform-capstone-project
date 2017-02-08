package edu.umdearborn.astronomyapp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;

import edu.umdearborn.astronomyapp.util.json.View;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "unitOptionId"))
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"unitId", "optionQuestionId"}),
    indexes = @Index(columnList = "optionQuestionId"))
@JsonIgnoreProperties("id")
public class UnitOption extends AbstractOption {

  private static final long serialVersionUID = -2298005560218235570L;

  @JsonUnwrapped
  @JsonView(View.Student.class)
  @Valid
  @NotNull
  @OneToOne
  @JoinColumn(name = "unitId")
  private Unit unit;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public Unit getUnit() {
    return unit;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
