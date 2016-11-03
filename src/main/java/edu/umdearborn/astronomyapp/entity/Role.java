package edu.umdearborn.astronomyapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@IdClass(Role.class)
@JsonInclude(Include.NON_EMPTY)
public class Role implements Serializable {
  
  private static final long serialVersionUID = 3076199305479713134L;

  @JsonIgnore
  @Id
  @ManyToOne
  @JoinColumn(name = "email", nullable = false)
  private AstroAppUser user;
  
  @Id
  @Column(nullable = false)
  private String role;

  public AstroAppUser getUser() {
    return user;
  }

  public void setUser(AstroAppUser user) {
    this.user = user;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
  
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, "user");
  }

}
