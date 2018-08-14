package com.ank.noteshelf.response;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteResponse {
	
	private UUID noteId;
	
	private String noteTitle;
	
	private String noteContent;
	
	private Date createdDate;
	 
	private Date updatedDate;

}
