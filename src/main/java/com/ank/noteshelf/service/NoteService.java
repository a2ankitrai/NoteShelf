package com.ank.noteshelf.service;

import java.util.List;

import com.ank.noteshelf.resource.NoteInput;
import com.ank.noteshelf.response.NoteResponse;

public interface NoteService {
	
	NoteResponse createNote(NoteInput noteInput, int userId);
	
	NoteResponse updateNote(NoteInput noteInput, int noteId, int userId);
	
	List<NoteResponse> getAllNotesByUser(int userId);
	
	NoteResponse getNoteById(int noteId, int userId);
	
	Boolean deleteNoteById(int noteId, int userId);

}
