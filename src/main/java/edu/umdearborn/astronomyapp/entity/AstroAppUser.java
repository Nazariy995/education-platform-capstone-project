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
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import edu.umdearborn.astronomyapp.validation.annotation.LogicalAppRoles;
import edu.umdearborn.astronomyapp.validation.group.ValidationGroup;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "userId"))
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId"}))
@JsonIgnoreProperties("id")
public class AstroAppUser extends AbstractGeneratedId {

  public static enum Role {
    ADMIN, INSTRUCTOR, USER;

    public String roleValue() {
      return "ROLE_" + toString();
    }
  }

  private static final long serialVersionUID = -7245103766854987243L;

  @Email
  @NotNull
  @Column(updatable = false)
  @Size(min = 5, max = 255)
  private String email;

  @NotNull
  @Size(min = 1, max = 35)
  private String firstName;

  @NotNull
  @JsonIgnore
  private boolean isEnabled = true;


  @NotNull
  @JsonIgnore
  private boolean isPasswordNonExpired = true;

  @NotNull
  @JsonIgnore
  private boolean isUserNonExpired = true;

  @NotNull
  @JsonIgnore
  private boolean isUserNonLocked = true;

  @NotNull
  @Size(min = 1, max = 35)
  private String lastName;

  @NotNull(groups = ValidationGroup.Checkin.class)
  @JsonProperty(access = Access.WRITE_ONLY)
  @Column(length = 62, nullable = false)
  private String password;

  @LogicalAppRoles
  @ElementCollection(targetClass = Role.class)
  @JoinTable(name = "ROLE", uniqueConstraints = @UniqueConstraint(columnNames = {"role", "userId"}),
      joinColumns = @JoinColumn(name = "userId"), indexes = @Index(columnList = "userId"))
  @Column(name = "role", length = 10)
  @Enumerated(EnumType.STRING)
  @NotEmpty
  @JsonProperty("appRoles")
  private Set<Role> roles = new HashSet<>();

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, "password");
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPassword() {
    return password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, "password");
  }

  @JsonIgnore
  public boolean isEnabled() {
    return isEnabled;
  }

  @JsonIgnore
  public boolean isPasswordNonExpired() {
    return isPasswordNonExpired;
  }

  @JsonIgnore
  public boolean isUserNonExpired() {
    return isUserNonExpired;
  }

  @JsonIgnore
  public boolean isUserNonLocked() {
    return isUserNonLocked;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPasswordNonExpired(boolean isPasswordNonExpired) {
    this.isPasswordNonExpired = isPasswordNonExpired;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public void setUserNonExpired(boolean isUserNonExpired) {
    this.isUserNonExpired = isUserNonExpired;
  }

  public void setUserNonLocked(boolean isUserNonLocked) {
    this.isUserNonLocked = isUserNonLocked;
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
