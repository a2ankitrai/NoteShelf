package com.ank.noteshelf.resource;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteInput {

	@JsonProperty("note_title")
	private String noteTitle;
	
	@NotNull
    @NotEmpty(message="Note's content cannot be empty")
	@JsonProperty("note_content")
	private String noteContent;
}
