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
 * NsNotesMetaData generated by hbm2java
 */
@Entity
@Table(name = "NS_NOTES_META_DATA", catalog = "Note_shelf_DB")
public class NsNotesMetaData implements java.io.Serializable {


  private byte[] noteId;
  private byte[] userId;
  private String noteNosqlId;
  private Date createdDate;
  private Date updatedDate;

  public NsNotesMetaData() {}


  public NsNotesMetaData(byte[] noteId) {
    this.noteId = noteId;
  }

  public NsNotesMetaData(byte[] noteId, byte[] userId, String noteNosqlId, Date createdDate,
      Date updatedDate) {
    this.noteId = noteId;
    this.userId = userId;
    this.noteNosqlId = noteNosqlId;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }

  @Id


  @Column(name = "NOTE_ID", unique = true, nullable = false)
  public byte[] getNoteId() {
    return this.noteId;
  }

  public void setNoteId(byte[] noteId) {
    this.noteId = noteId;
  }


  @Column(name = "USER_ID")
  public byte[] getUserId() {
    return this.userId;
  }

  public void setUserId(byte[] userId) {
    this.userId = userId;
  }


  @Column(name = "NOTE_NOSQL_ID", length = 100)
  public String getNoteNosqlId() {
    return this.noteNosqlId;
  }

  public void setNoteNosqlId(String noteNosqlId) {
    this.noteNosqlId = noteNosqlId;
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


