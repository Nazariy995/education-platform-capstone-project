package edu.umdearborn.astronomyapp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "groupMemberId"))
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"courseUserId", "moduleGroupId"}),
    indexes = @Index(columnList = "moduleGroupId"))
public class GroupMember extends AbstractGeneratedId {

  private static final long serialVersionUID = -1177655419609052637L;

  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "courseUserId", updatable = false)
  private CourseUser courseUser;

  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "moduleGroupId", updatable = false)
  private ModuleGroup moduleGroup;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public CourseUser getCourseUser() {
    return courseUser;
  }

  public ModuleGroup getModuleGroup() {
    return moduleGroup;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public void setCourseUser(CourseUser courseUser) {
    this.courseUser = courseUser;
  }

  public void setModuleGroup(ModuleGroup moduleGroup) {
    this.moduleGroup = moduleGroup;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
