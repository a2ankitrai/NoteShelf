package com.ank.noteshelf.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteResponse {
	
	private int noteId;
	
	private String noteTitle;
	
	private String noteContent;
	
	private Date createdDate;
	 
	private Date updatedDate;

}
