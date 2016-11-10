package edu.umdearborn.astronomyapp.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "moduleLockId"))
@Table(indexes = {@Index(columnList = "moduleId"), @Index(columnList = "moduleGroupId"),
    @Index(columnList = "expirationTimestamp")})
public class ModuleLock extends AbstractGeneratedId {

  private static final long serialVersionUID = 5772684472161202294L;

  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "moduleId", updatable = false)
  private Module module;

  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "courseUserId", updatable = false)
  private CourseUser user;

  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "moduleGroupId", updatable = false)
  private ModuleGroup group;

  @NotNull
  @Future
  private Date expirationTimestamp;

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  public CourseUser getUser() {
    return user;
  }

  public void setUser(CourseUser user) {
    this.user = user;
  }

  public ModuleGroup getGroup() {
    return group;
  }

  public void setGroup(ModuleGroup group) {
    this.group = group;
  }

  public Date getExpirationTimestamp() {
    return expirationTimestamp;
  }

  public void setExpirationTimestamp(Date expirationTimestamp) {
    this.expirationTimestamp = expirationTimestamp;
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
