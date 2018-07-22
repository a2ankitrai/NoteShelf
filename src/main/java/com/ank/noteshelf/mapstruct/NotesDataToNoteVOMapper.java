package com.ank.noteshelf.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ank.noteshelf.model.NsNotesData;
import com.ank.noteshelf.model.NsNotesMetaData;
import com.ank.noteshelf.vo.NoteVO;

@Mapper
public interface NotesDataToNoteVOMapper {

	@Mapping(source = "noteData.noteTitle", target = "noteTitle")
    @Mapping(source = "noteData.noteContent", target = "noteContent")
	@Mapping(source = "noteMetaData.noteId", target = "noteId")
	@Mapping(source = "noteMetaData.createdDate", target = "createdDate")
	@Mapping(source = "noteMetaData.updatedDate", target = "updatedDate")
	NoteVO mapNotesToNoteVO(NsNotesData noteData, NsNotesMetaData noteMetaData);
	
	
	
}
