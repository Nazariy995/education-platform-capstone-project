package edu.umdearborn.astronomyapp.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "token"))
@Table(indexes = @Index(columnList = "userId"))
public class PasswordRecoveryRequest extends AbstractGeneratedId {

  private static final long serialVersionUID = -834154778189285366L;

  @NotNull
  @Future
  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = false)
  private Date expirationTimestamp;

  @Valid
  @NotNull
  @ManyToOne
  @JoinColumn(name = "userId", updatable = false)
  private AstroAppUser user;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public Date getExpirationTimestamp() {
    return expirationTimestamp;
  }

  public AstroAppUser getUser() {
    return user;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public void setExpirationTimestamp(Date expirationTimestamp) {
    this.expirationTimestamp = expirationTimestamp;
  }

  public void setUser(AstroAppUser user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
