package com.ank.noteshelf.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "NS_NOTES")
public class NsNotesData {

    @Id
    private String id;

    private String noteTitle;
    private String noteContent;

}