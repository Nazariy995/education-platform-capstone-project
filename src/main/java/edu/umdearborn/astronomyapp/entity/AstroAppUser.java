package edu.umdearborn.astronomyapp.entity;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "userId"))
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId"}))
@JsonIgnoreProperties("id")
public class AstroAppUser extends AbstractGeneratedId {

  private static final long serialVersionUID = -7245103766854987243L;

  public static enum Role {
    USER, INSTRUCTOR, ADMIN, NUL;

    public String roleValue() {
      return "ROLE_" + toString();
    }
  }

  @NotNull
  @Column(updatable = false)
  @Size(min = 5, max = 255)
  private String email;

  @NotNull
  @Size(min = 1, max = 35)
  private String firstName;

  @NotNull
  @Size(min = 1, max = 35)
  private String lastName;

  @NotNull
  @JsonProperty(access = Access.WRITE_ONLY)
  @Column(length = 62)
  private String password;

  @NotNull
  private boolean isUserNonExpired = true;

  @NotNull
  private boolean isPasswordNonExpired = true;

  @NotNull
  private boolean isUserNonLocked = true;

  @NotNull
  private boolean isEnabled = true;

  @NotNull
  @ElementCollection(targetClass = Role.class)
  @JoinTable(name = "ROLE", uniqueConstraints = @UniqueConstraint(columnNames = {"role", "userId"}),
      joinColumns = @JoinColumn(name = "userId"), indexes = @Index(columnList = "userId"))
  @Column(name = "role", length = 10)
  @Enumerated(EnumType.STRING)
  @JsonProperty("appRoles")
  private Set<Role> roles = new HashSet<>();

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @JsonIgnore
  public boolean isUserNonExpired() {
    return isUserNonExpired;
  }

  public void setUserNonExpired(boolean isUserNonExpired) {
    this.isUserNonExpired = isUserNonExpired;
  }

  @JsonIgnore
  public boolean isPasswordNonExpired() {
    return isPasswordNonExpired;
  }

  public void setPasswordNonExpired(boolean isPasswordNonExpired) {
    this.isPasswordNonExpired = isPasswordNonExpired;
  }

  @JsonIgnore
  public boolean isUserNonLocked() {
    return isUserNonLocked;
  }

  public void setUserNonLocked(boolean isUserNonLocked) {
    this.isUserNonLocked = isUserNonLocked;
  }

  @JsonIgnore
  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, "password");
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, "password");
  }

  @Override
  public String toString() {
    return (new ReflectionToStringBuilder(this) {

      @Override
      protected Object getValue(Field field)
          throws IllegalArgumentException, IllegalAccessException {
        if ("password".equalsIgnoreCase(field.getName()) && super.getValue(field) != null) {
          return "******";
        }
        return super.getValue(field);
      }
    }).toString();
  }

}
