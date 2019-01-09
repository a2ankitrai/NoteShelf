package com.ank.noteshelf.model;
// Generated Dec 22, 2018 12:36:32 PM by Hibernate Tools 5.2.11.Final


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * NsTokenStore generated by hbm2java
 */
@Entity
@Table(name = "NS_TOKEN_STORE", catalog = "Note_shelf_DB")
public class NsTokenStore implements java.io.Serializable {


  private byte[] tokenStoreId;
  private String tokenType;
  private String tokenValue;
  private byte[] userId;
  private Boolean isExpired;
  private Date createDate;
  private Date expiredDate;

  public NsTokenStore() {}


  public NsTokenStore(byte[] tokenStoreId, String tokenType, String tokenValue) {
    this.tokenStoreId = tokenStoreId;
    this.tokenType = tokenType;
    this.tokenValue = tokenValue;
  }

  public NsTokenStore(byte[] tokenStoreId, String tokenType, String tokenValue, byte[] userId,
      Boolean isExpired, Date createDate, Date expiredDate) {
    this.tokenStoreId = tokenStoreId;
    this.tokenType = tokenType;
    this.tokenValue = tokenValue;
    this.userId = userId;
    this.isExpired = isExpired;
    this.createDate = createDate;
    this.expiredDate = expiredDate;
  }

  @Id


  @Column(name = "TOKEN_STORE_ID", unique = true, nullable = false)
  public byte[] getTokenStoreId() {
    return this.tokenStoreId;
  }

  public void setTokenStoreId(byte[] tokenStoreId) {
    this.tokenStoreId = tokenStoreId;
  }


  @Column(name = "TOKEN_TYPE", nullable = false, length = 100)
  public String getTokenType() {
    return this.tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }


  @Column(name = "TOKEN_VALUE", nullable = false, length = 45)
  public String getTokenValue() {
    return this.tokenValue;
  }

  public void setTokenValue(String tokenValue) {
    this.tokenValue = tokenValue;
  }


  @Column(name = "USER_ID")
  public byte[] getUserId() {
    return this.userId;
  }

  public void setUserId(byte[] userId) {
    this.userId = userId;
  }


  @Column(name = "IS_EXPIRED")
  public Boolean getIsExpired() {
    return this.isExpired;
  }

  public void setIsExpired(Boolean isExpired) {
    this.isExpired = isExpired;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATE_DATE", length = 19)
  public Date getCreateDate() {
    return this.createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "EXPIRED_DATE", length = 19)
  public Date getExpiredDate() {
    return this.expiredDate;
  }

  public void setExpiredDate(Date expiredDate) {
    this.expiredDate = expiredDate;
  }



}


