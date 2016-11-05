package edu.umdearborn.astronomyapp.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
public class PasswordRecoveryRequest implements Serializable{

  private static final long serialVersionUID = -834154778189285366L;

  @Id
  private String token;
  
  @ManyToOne
  @JoinColumn(name = "email", nullable = false, updatable = false)
  private AstroAppUser user;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date expirationTimestamp;

  
  public String getToken() {
    return token;
  }

  
  public void setToken(String token) {
    this.token = token;
  }

  
  public AstroAppUser getUser() {
    return user;
  }

  
  public void setUser(AstroAppUser user) {
    this.user = user;
  }

  
  public Date getExpirationTimestamp() {
    return expirationTimestamp;
  }

  
  public void setExpirationTimestamp(Date expirationTimestamp) {
    this.expirationTimestamp = expirationTimestamp;
  }
  
  @PrePersist
  protected void prePersist() {
    token = UUID.randomUUID().toString();
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, 1);
    expirationTimestamp = calendar.getTime();
  }
  
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, "user");
  }
  
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, "user");
  }
  
  @Override
  public String toString() {
    return new ReflectionToStringBuilder(this).setExcludeFieldNames("user").toString();
  }
  
}
