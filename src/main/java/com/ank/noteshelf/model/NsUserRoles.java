package com.ank.noteshelf.model;
// Generated Aug 15, 2018 3:31:51 AM by Hibernate Tools 5.2.11.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * NsUserRoles generated by hbm2java
 */
@Entity
@Table(name = "NS_USER_ROLES", catalog = "Note_shelf_DB")
public class NsUserRoles implements java.io.Serializable {

    private byte[] roleId;
    private byte[] userId;
    private String roleName;
    private Date createdDate;
    private Date updatedDate;

    public NsUserRoles() {
    }

    public NsUserRoles(byte[] roleId, byte[] userId) {
	this.roleId = roleId;
	this.userId = userId;
    }

    public NsUserRoles(byte[] roleId, byte[] userId, String roleName, Date createdDate, Date updatedDate) {
	this.roleId = roleId;
	this.userId = userId;
	this.roleName = roleName;
	this.createdDate = createdDate;
	this.updatedDate = updatedDate;
    }

    @Id

    @Column(name = "ROLE_ID", unique = true, nullable = false)
    public byte[] getRoleId() {
	return this.roleId;
    }

    public void setRoleId(byte[] roleId) {
	this.roleId = roleId;
    }

    @Column(name = "USER_ID", nullable = false)
    public byte[] getUserId() {
	return this.userId;
    }

    public void setUserId(byte[] userId) {
	this.userId = userId;
    }

    @Column(name = "ROLE_NAME", length = 50)
    public String getRoleName() {
	return this.roleName;
    }

    public void setRoleName(String roleName) {
	this.roleName = roleName;
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
