package com.ank.noteshelf.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ank.noteshelf.mapstruct.NotesDataToNoteVOMapper;
import com.ank.noteshelf.model.NsNotesData;
import com.ank.noteshelf.model.NsNotesMetaData;
import com.ank.noteshelf.repository.NoteDataRepository;
import com.ank.noteshelf.repository.NoteMetaDataRepository;
import com.ank.noteshelf.resource.NoteInput;
import com.ank.noteshelf.service.NoteService;
import com.ank.noteshelf.util.NsMessageConstant;
import com.ank.noteshelf.vo.NoteVO;

@Service
public class NoteServiceImpl implements NoteService {

	public static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	@Autowired
	NoteDataRepository noteDataRepository;

	@Autowired
	NoteMetaDataRepository noteMetaDataRepository;
	
	NotesDataToNoteVOMapper noteDataMapper = Mappers.getMapper(NotesDataToNoteVOMapper.class);

	// TODO
	// add debug logger statements.
	
	@Transactional
	public NoteVO createNote(NoteInput noteInput, int userId) {

		NsNotesData noteData = new NsNotesData();

		noteData.setNoteTitle(noteInput.getNoteTitle());
		noteData.setNoteContent(noteInput.getNoteContent());

		noteData = noteDataRepository.save(noteData);

		NsNotesMetaData noteMetaData = new NsNotesMetaData();

		noteMetaData.setUserId(userId);
		noteMetaData.setNoteNosqlId(noteData.getId());

		Date now = new Date();
		noteMetaData.setCreatedDate(now);
		noteMetaData.setUpdatedDate(now);

		noteMetaData = noteMetaDataRepository.save(noteMetaData);
 
		NoteVO noteVo = noteDataMapper.mapNotesToNoteVO(noteData, noteMetaData);
  
		return noteVo; 
	}
	
	@Override
	@Transactional
	public NoteVO updateNote(NoteInput noteInput, int noteId,  int userId) {
		
		NoteVO noteVo = null;
		NsNotesData noteData = null;
		NsNotesMetaData noteMetaData = null;
		Optional<NsNotesMetaData> noteMetaDataOptional = Optional.ofNullable(noteMetaDataRepository.findByNoteIdAndUserId(noteId, userId));
		
		if(noteMetaDataOptional.isPresent()) {
			noteMetaData = noteMetaDataOptional.get();
			Optional<NsNotesData> noteDataOptional =  noteDataRepository.findById(noteMetaData.getNoteNosqlId()); 
			noteData = noteDataOptional.isPresent() ? noteDataOptional.get() : null;
		}
		
		if(noteData != null) {
				noteData.setNoteTitle(noteInput.getNoteTitle());
				noteData.setNoteContent(noteInput.getNoteContent());
				noteData = noteDataRepository.save(noteData);
				
				Date now = new Date();
				noteMetaData.setUpdatedDate(now);
				noteVo = noteDataMapper.mapNotesToNoteVO(noteData, noteMetaData);			
		}
		else {
			throw new EmptyResultDataAccessException(NsMessageConstant.NO_NOTE_FOUND_BY_ID,1);
		}
		
		return noteVo;
	}

	@Override
	public NoteVO getNoteById(int noteId, int userId) {

		Optional<NsNotesMetaData> noteMetaData = Optional.ofNullable(noteMetaDataRepository.findByNoteIdAndUserId(noteId, userId)); 
		Optional<NsNotesData> noteData = noteMetaData.isPresent() ? noteDataRepository.findById(noteMetaData.get().getNoteNosqlId()) : Optional.ofNullable(null); 
		NoteVO noteVO = null;
		
		if(noteData.isPresent()) {
			noteVO = noteDataMapper.mapNotesToNoteVO(noteData.get(), noteMetaData.get());
			return noteVO;
		}
		else {
			throw new EmptyResultDataAccessException(NsMessageConstant.NO_NOTE_FOUND_BY_ID,1);
		}
	}

	@Override
	public List<NoteVO> getAllNotesByUser(int userId) {

		List<NsNotesMetaData> notesMetaDataList = noteMetaDataRepository.findByUserId(userId);
  
//		List<NsNotesData> notesDataList = notesMetaDataList.stream()
//												.map(noteMetaData -> noteMetaData.getNoteNosqlId())
//												.map(noSqlId -> noteDataRepository.findById(noSqlId))
//												.filter(note -> note.isPresent())
//												.map(note -> note.get())
//												.collect(Collectors.toList());
//		
//		List<NoteVO> noteVOList = notesDataList.stream()
//								  .map(noteData -> convertNotesDataToNoteVO(noteData))
//								  .collect(Collectors.toList());
		
		List<NoteVO> noteVOList = notesMetaDataList.stream()
										.map(noteMetaData -> makeNoteVOFromNotesMetaData(noteMetaData))
										.collect(Collectors.toList());

		return noteVOList;
	}
	
	@Transactional
	public Boolean deleteNoteById(int noteId, int userId) {
		Boolean isDeleted = false;
		
//		Optional<NsNotesMetaData> noteMetaData = Optional.ofNullable(noteMetaDataRepository.deleteByNoteIdAndUserId(noteId, userId)); 
//		if(noteMetaData.isPresent()) {
//			noteDataRepository.deleteById(noteMetaData.get().getNoteNosqlId()); 
//			isDeleted = true;
//		}
//		
//		return isDeleted;
		
		// below is wrong. get the notemeta data and delete the actual note as well.
		
		Optional<Integer> val = Optional.ofNullable(noteMetaDataRepository.deleteByNoteIdAndUserId(noteId, userId)); 
		if(val.isPresent()) {
			System.out.println(val);
			isDeleted = true;
		}
		else {
			throw new EmptyResultDataAccessException(NsMessageConstant.NO_NOTE_FOUND_BY_ID,1);
		}
		
		return isDeleted;
	}
	
	private NoteVO makeNoteVOFromNotesMetaData(NsNotesMetaData noteMetaData) {
		
		Optional<NsNotesData> noteDataOptional = noteDataRepository.findById(noteMetaData.getNoteNosqlId());
		NsNotesData noteData = null;
		
		if(noteDataOptional.isPresent()) {
			noteData = noteDataOptional.get();
		}
		 
		NoteVO noteVO = noteDataMapper.mapNotesToNoteVO(noteData, noteMetaData);
		
		return noteVO;
	} 
			
}
