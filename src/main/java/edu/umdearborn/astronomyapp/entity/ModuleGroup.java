package edu.umdearborn.astronomyapp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "moduleGroupId"))
@Table(indexes = @Index(columnList = "moduleId"))
public class ModuleGroup extends AbstractGeneratedId {

  private static final long serialVersionUID = -5650773937729046598L;

  @JsonProperty("isFinalized")
  private boolean isLocked = false;

  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "moduleId", updatable = false)
  private Module module;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public Module getModule() {
    return module;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public boolean isLocked() {
    return isLocked;
  }

  public void setLocked(boolean isLocked) {
    this.isLocked = isLocked;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
