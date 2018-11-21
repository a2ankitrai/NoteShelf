package com.ank.noteshelf.response;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteResponse {

    @JsonProperty("note_id")
    private UUID noteId;

    @JsonProperty("note_title")
    private String noteTitle;

    @JsonProperty("note_content")
    private String noteContent;

    @JsonProperty("created_date")
    private Date createdDate;

    @JsonProperty("updated_date")
    private Date updatedDate;

}
