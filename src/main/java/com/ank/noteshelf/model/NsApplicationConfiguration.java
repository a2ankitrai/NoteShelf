package com.ank.noteshelf.model;
// Generated Aug 15, 2018 3:31:51 AM by Hibernate Tools 5.2.11.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * NsApplicationConfiguration generated by hbm2java
 */
@Entity
@Table(name = "NS_APPLICATION_CONFIGURATION", catalog = "Note_shelf_DB", uniqueConstraints = @UniqueConstraint(columnNames = "CONFIGURATION_NAME"))
public class NsApplicationConfiguration implements java.io.Serializable {

    private byte[] configurationId;
    private String configurationName;
    private String configurationValue;
    private Byte isLazyLoadDisabled;
    private Date createdDate;
    private Date updatedDate;

    public NsApplicationConfiguration() {
    }

    public NsApplicationConfiguration(byte[] configurationId, String configurationName) {
	this.configurationId = configurationId;
	this.configurationName = configurationName;
    }

    public NsApplicationConfiguration(byte[] configurationId, String configurationName, String configurationValue,
	    Byte isLazyLoadDisabled, Date createdDate, Date updatedDate) {
	this.configurationId = configurationId;
	this.configurationName = configurationName;
	this.configurationValue = configurationValue;
	this.isLazyLoadDisabled = isLazyLoadDisabled;
	this.createdDate = createdDate;
	this.updatedDate = updatedDate;
    }

    @Id

    @Column(name = "CONFIGURATION_ID", unique = true, nullable = false)
    public byte[] getConfigurationId() {
	return this.configurationId;
    }

    public void setConfigurationId(byte[] configurationId) {
	this.configurationId = configurationId;
    }

    @Column(name = "CONFIGURATION_NAME", unique = true, nullable = false, length = 100)
    public String getConfigurationName() {
	return this.configurationName;
    }

    public void setConfigurationName(String configurationName) {
	this.configurationName = configurationName;
    }

    @Column(name = "CONFIGURATION_VALUE", length = 65535)
    public String getConfigurationValue() {
	return this.configurationValue;
    }

    public void setConfigurationValue(String configurationValue) {
	this.configurationValue = configurationValue;
    }

    @Column(name = "IS_LAZY_LOAD_DISABLED")
    public Byte getIsLazyLoadDisabled() {
	return this.isLazyLoadDisabled;
    }

    public void setIsLazyLoadDisabled(Byte isLazyLoadDisabled) {
	this.isLazyLoadDisabled = isLazyLoadDisabled;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", length = 19)
    public Date getCreatedDate() {
	return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE", length = 19)
    public Date getUpdatedDate() {
	return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
	this.updatedDate = updatedDate;
    }

}
