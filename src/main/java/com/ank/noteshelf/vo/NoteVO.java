package com.ank.noteshelf.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteVO {
	
	private int noteId;
	
	private String noteTitle;
	
	private String noteContent;
	
	private Date createdDate;
	 
	private Date updatedDate;

}
