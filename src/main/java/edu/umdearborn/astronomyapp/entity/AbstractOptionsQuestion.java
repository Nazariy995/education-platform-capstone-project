package edu.umdearborn.astronomyapp.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@MappedSuperclass
public class AbstractOptionsQuestion<T extends AbstractOption> extends AbstractGeneratedId
    implements MachineGradeable {

  private static final long serialVersionUID = -3078009736538827491L;

  @Size(min = 1)
  @OneToMany
  @JoinColumn(name = "optionQuestionId")
  private Set<T> options = new HashSet<>();

  public Set<T> getOptions() {
    return options;
  }

  public void setOptions(Set<T> options) {
    this.options = options;
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
