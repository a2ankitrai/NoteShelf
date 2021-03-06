package com.ank.noteshelf.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ank.noteshelf.input.NoteInput;
import com.ank.noteshelf.mapstruct.NotesDataToNoteVOMapper;
import com.ank.noteshelf.model.NsNotesData;
import com.ank.noteshelf.model.NsNotesMetaData;
import com.ank.noteshelf.repository.NoteDataRepository;
import com.ank.noteshelf.repository.NoteMetaDataRepository;
import com.ank.noteshelf.resource.NsMessageConstant;
import com.ank.noteshelf.response.NoteResponse;
import com.ank.noteshelf.service.NoteService;
import com.ank.noteshelf.util.UuidUtils;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteDataRepository noteDataRepository;

    @Autowired
    NoteMetaDataRepository noteMetaDataRepository;

    @Transactional
    public NoteResponse createNote(NoteInput noteInput, byte[] userId) {

	NsNotesData noteData = new NsNotesData();

	noteData.setNoteTitle(noteInput.getNoteTitle());
	noteData.setNoteContent(noteInput.getNoteContent());

	noteData = noteDataRepository.save(noteData);

	NsNotesMetaData noteMetaData = new NsNotesMetaData();

	noteMetaData.setNoteId(UuidUtils.generateUuidBytes());
	noteMetaData.setUserId(userId);
	noteMetaData.setNoteNosqlId(noteData.getId());

	Date now = new Date();
	noteMetaData.setCreatedDate(now);
	noteMetaData.setUpdatedDate(now);

	noteMetaData = noteMetaDataRepository.save(noteMetaData);

	NoteResponse noteVo = NotesDataToNoteVOMapper.INSTANCE.mapNotesToNoteVO(noteData, noteMetaData);

	return noteVo;
    }

    @Override
    @Transactional
    public NoteResponse updateNote(NoteInput noteInput, byte[] noteId, byte[] userId) {

	NoteResponse noteVo = null;
	NsNotesData noteData = null;
	NsNotesMetaData noteMetaData = null;
	Optional<NsNotesMetaData> noteMetaDataOptional = Optional
		.ofNullable(noteMetaDataRepository.findByNoteIdAndUserId(noteId, userId));

	if (noteMetaDataOptional.isPresent()) {
	    noteMetaData = noteMetaDataOptional.get();
	    Optional<NsNotesData> noteDataOptional = noteDataRepository.findById(noteMetaData.getNoteNosqlId());
	    noteData = noteDataOptional.isPresent() ? noteDataOptional.get() : null;
	}

	if (noteData != null) {
	    noteData.setNoteTitle(noteInput.getNoteTitle());
	    noteData.setNoteContent(noteInput.getNoteContent());
	    noteData = noteDataRepository.save(noteData);

	    Date now = new Date();
	    noteMetaData.setUpdatedDate(now);
	    noteVo = NotesDataToNoteVOMapper.INSTANCE.mapNotesToNoteVO(noteData, noteMetaData);
	} else {
	    throw new EmptyResultDataAccessException(NsMessageConstant.NO_NOTE_FOUND_BY_ID, 1);
	}

	return noteVo;
    }

    @Override
    public NoteResponse getNoteById(byte[] noteId, byte[] userId) {

	Optional<NsNotesMetaData> noteMetaData = Optional
		.ofNullable(noteMetaDataRepository.findByNoteIdAndUserId(noteId, userId));
	Optional<NsNotesData> noteData = noteMetaData.isPresent()
		? noteDataRepository.findById(noteMetaData.get().getNoteNosqlId())
		: Optional.ofNullable(null);
	NoteResponse noteVO = null;

	if (noteData.isPresent()) {
	    noteVO = NotesDataToNoteVOMapper.INSTANCE.mapNotesToNoteVO(noteData.get(), noteMetaData.get());
	    return noteVO;
	} else {
	    throw new EmptyResultDataAccessException(NsMessageConstant.NO_NOTE_FOUND_BY_ID, 1);
	}
    }

    @Override
    public List<NoteResponse> getAllNotesByUser(byte[] userId) {

	List<NsNotesMetaData> notesMetaDataList = noteMetaDataRepository.findByUserId(userId);

	// List<NsNotesData> notesDataList = notesMetaDataList.stream()
	// .map(noteMetaData -> noteMetaData.getNoteNosqlId())
	// .map(noSqlId -> noteDataRepository.findById(noSqlId))
	// .filter(note -> note.isPresent())
	// .map(note -> note.get())
	// .collect(Collectors.toList());
	//
	// List<NoteVO> noteVOList = notesDataList.stream()
	// .map(noteData -> convertNotesDataToNoteVO(noteData))
	// .collect(Collectors.toList());

	List<NoteResponse> noteVOList = notesMetaDataList.stream()
		.map(noteMetaData -> makeNoteVOFromNotesMetaData(noteMetaData)).collect(Collectors.toList());

	return noteVOList;
    }

    @Transactional
    public Boolean deleteNoteById(byte[] noteId, byte[] userId) {
	Boolean isDeleted = false;
	Optional<Integer> val = Optional.ofNullable(noteMetaDataRepository.deleteByNoteIdAndUserId(noteId, userId));
	if (val.isPresent()) {
	    isDeleted = true;
	} else {
	    throw new EmptyResultDataAccessException(NsMessageConstant.NO_NOTE_FOUND_BY_ID, 1);
	}

	return isDeleted;
    }

    private NoteResponse makeNoteVOFromNotesMetaData(NsNotesMetaData noteMetaData) {

	Optional<NsNotesData> noteDataOptional = noteDataRepository.findById(noteMetaData.getNoteNosqlId());
	NsNotesData noteData = null;

	if (noteDataOptional.isPresent()) {
	    noteData = noteDataOptional.get();
	}

	NoteResponse noteVO = NotesDataToNoteVOMapper.INSTANCE.mapNotesToNoteVO(noteData, noteMetaData);

	return noteVO;
    }

}
