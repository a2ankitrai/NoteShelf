package com.ank.noteshelf.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ank.noteshelf.model.NsNotesData;
import com.ank.noteshelf.model.NsNotesMetaData;
import com.ank.noteshelf.response.NoteResponse;

@Mapper(uses = {NsCommonMapperFunctions.class})
public interface NotesDataToNoteVOMapper {

	@Mapping(source = "noteData.noteTitle", target = "noteTitle")
    @Mapping(source = "noteData.noteContent", target = "noteContent")
	@Mapping(source = "noteMetaData.noteId", target = "noteId", qualifiedByName="mapByteToUuid")
	@Mapping(source = "noteMetaData.createdDate", target = "createdDate")
	@Mapping(source = "noteMetaData.updatedDate", target = "updatedDate")
	NoteResponse mapNotesToNoteVO(NsNotesData noteData, NsNotesMetaData noteMetaData);
	
	
	
}
