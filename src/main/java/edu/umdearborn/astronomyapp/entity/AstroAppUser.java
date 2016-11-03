package edu.umdearborn.astronomyapp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonInclude(Include.NON_NULL)
@Entity
@Table(name = "\"user\"")
public class AstroAppUser implements Serializable {

  private static final long serialVersionUID = -7245103766854987243L;

  @Id
  @JsonProperty("username")
  private String email;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String passwordHash;

  @Column(nullable = false)
  private boolean isUserNonExpired = true;

  @Column(nullable = false)
  private boolean isPasswordNonExpired = true;

  @Column(nullable = false)
  private boolean isUserNonLocked = true;

  @Column(nullable = false)
  private boolean isEnabled = true;
  
  @JsonIdentityReference(alwaysAsId = true)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "role")
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
  private Collection<Role> roles = new ArrayList<>();

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
  @JsonIgnore
  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public boolean isUserNonExpired() {
    return isUserNonExpired;
  }

  public void setUserNonExpired(boolean isUserNonExpired) {
    this.isUserNonExpired = isUserNonExpired;
  }

  public boolean isPasswordNonExpired() {
    return isPasswordNonExpired;
  }

  public void setPasswordNonExpired(boolean isPasswordNonExpired) {
    this.isPasswordNonExpired = isPasswordNonExpired;
  }

  public boolean isUserNonLocked() {
    return isUserNonLocked;
  }

  public void setUserNonLocked(boolean isUserNonLocked) {
    this.isUserNonLocked = isUserNonLocked;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  public Collection<Role> getRoles() {
    return roles;
  }

  public void setRoles(Collection<Role> roles) {
    this.roles = roles;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

}
