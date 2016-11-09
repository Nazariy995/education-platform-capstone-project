package edu.umdearborn.astronomyapp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"pageId", "itemOrder"}))
@AttributeOverride(name = "id", column = @Column(name = "pageItemId"))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "pageItemType")
@DiscriminatorValue("TEXT")
public class PageItem extends AbstractGeneratedId {

  private static final long serialVersionUID = 2553850340823580376L;

  public static enum PageItemType {
    TEXT, QUESTION, NUL;
  }

  @NotNull
  @ManyToOne
  @JoinColumn(name = "pageId")
  private Page page;

  @NotNull
  @Enumerated(EnumType.STRING)
  private PageItemType pageItemType = PageItemType.NUL;

  @NotNull
  @Min(0)
  @Column(name = "itemOrder")
  private int order = 0;

  @NotNull
  private String humanReadableText;

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public PageItemType getPageItemType() {
    return pageItemType;
  }

  public void setPageItemType(PageItemType pageItemType) {
    this.pageItemType = pageItemType;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public String getHumanReadableText() {
    return humanReadableText;
  }

  public void setHumanReadableText(String humanReadableText) {
    this.humanReadableText = humanReadableText;
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
