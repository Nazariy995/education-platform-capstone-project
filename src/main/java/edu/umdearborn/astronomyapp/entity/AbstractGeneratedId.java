package edu.umdearborn.astronomyapp.entity;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import edu.umdearborn.astronomyapp.util.json.View;

@MappedSuperclass
@JsonInclude(NON_NULL)
public abstract class AbstractGeneratedId implements Serializable {

  private static final long serialVersionUID = -7979849207672422242L;

  @JsonView(View.Student.class)
  @Id
  protected String id;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public String getId() {
    return id;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @PrePersist
  public void prePersist() {
    id = UUID.randomUUID().toString();
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
