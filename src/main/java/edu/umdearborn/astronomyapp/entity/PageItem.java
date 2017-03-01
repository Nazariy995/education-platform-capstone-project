package edu.umdearborn.astronomyapp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import edu.umdearborn.astronomyapp.util.json.View;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"pageId", "itemOrder"}),
    indexes = @Index(columnList = "pageId"))
@AttributeOverride(name = "id", column = @Column(name = "pageItemId"))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "pageItemType")
@DiscriminatorValue("TEXT")
public class PageItem extends AbstractGeneratedId {

  public static enum PageItemType {
    QUESTION, TEXT;
  }

  private static final long serialVersionUID = 2553850340823580376L;

  @JsonView(View.Student.class)
  @NotNull
  @Size(min = 1)
  private String humanReadableText;

  @JsonView(View.Student.class)
  @NotNull
  @Min(1)
  @Column(name = "itemOrder")
  private int order = 1;

  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "pageId")
  private Page page;

  @JsonView(View.Student.class)
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(length = 8)
  private PageItemType pageItemType;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public String getHumanReadableText() {
    return humanReadableText;
  }

  public int getOrder() {
    return order;
  }

  public Page getPage() {
    return page;
  }

  public PageItemType getPageItemType() {
    return pageItemType;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public void setHumanReadableText(String humanReadableText) {
    this.humanReadableText = humanReadableText;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public void setPageItemType(PageItemType pageItemType) {
    this.pageItemType = pageItemType;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
