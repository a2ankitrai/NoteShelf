package com.ank.noteshelf.service;

import java.util.List;

import com.ank.noteshelf.resource.NoteInput;
import com.ank.noteshelf.vo.NoteVO;

public interface NoteService {
	
	NoteVO createNote(NoteInput noteInput, int userId);
	
	List<NoteVO> getAllNotesByUser(int userId);
	
	NoteVO getNoteById(int noteId, int userId);

}
