package edu.umdearborn.astronomyapp.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import edu.umdearborn.astronomyapp.util.json.View;

@MappedSuperclass
public abstract class AbstractOptionsQuestion<T extends AbstractOption> extends Question
    implements MachineGradeable {

  private static final long serialVersionUID = -3078009736538827491L;

  @JsonView(View.Student.class)
  @JsonDeserialize(as = HashSet.class)
  @Valid
  @Size(min = 1)
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "optionQuestionId")
  private Set<T> options = new HashSet<>();

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public Set<T> getOptions() {
    return options;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public void setOptions(Set<T> options) {
    this.options = options;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
