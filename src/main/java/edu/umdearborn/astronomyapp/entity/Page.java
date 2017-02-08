package edu.umdearborn.astronomyapp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"moduleId", "pageOrder"}),
    indexes = {@Index(columnList = "moduleId"), @Index(columnList = "pageOrder")})
@AttributeOverride(name = "id", column = @Column(name = "pageId"))
public class Page extends AbstractGeneratedId {

  private static final long serialVersionUID = 3881270380048176L;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private String humanReadableText;

  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "moduleId", updatable = false)
  private Module module;

  @NotNull
  @Min(1)
  @Column(name = "pageOrder")
  private int order = 1;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public String getHumanReadableText() {
    return humanReadableText;
  }

  public Module getModule() {
    return module;
  }

  public int getOrder() {
    return order;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public void setHumanReadableText(String humanReadableText) {
    this.humanReadableText = humanReadableText;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
