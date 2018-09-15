package com.ank.noteshelf.service;

import java.util.List;

import com.ank.noteshelf.input.NoteInput;
import com.ank.noteshelf.response.NoteResponse;

public interface NoteService {

    NoteResponse createNote(NoteInput noteInput, byte[] userId);

    NoteResponse updateNote(NoteInput noteInput, byte[] noteId, byte[] userId);

    List<NoteResponse> getAllNotesByUser(byte[] userId);

    NoteResponse getNoteById(byte[] noteId, byte[] userId);

    Boolean deleteNoteById(byte[] noteId, byte[] userId);

}
