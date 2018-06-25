package com.ank.noteshelf.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ank.noteshelf.model.NsNotesData;
import com.ank.noteshelf.model.NsNotesMetaData;
import com.ank.noteshelf.repository.NoteDataRepository;
import com.ank.noteshelf.repository.NoteMetaDataRepository;
import com.ank.noteshelf.resource.NoteInput;
import com.ank.noteshelf.service.NoteService;
import com.ank.noteshelf.vo.NoteVO;

@Service
public class NoteServiceImpl implements NoteService {

	public static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	@Autowired
	NoteDataRepository noteDataRepository;

	@Autowired
	NoteMetaDataRepository noteMetaDataRepository;

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

		NoteVO noteVo = new NoteVO();

		noteVo.setNoteId(noteMetaData.getNoteId());
		noteVo.setNoteTitle(noteData.getNoteTitle());
		noteVo.setCreatedDate(now);
		noteVo.setUpdatedDate(now);

		return noteVo;

	}

	@Override
	public NoteVO getNoteById(int noteId, int userId) {

		Optional<NsNotesMetaData> noteMetaData = Optional.ofNullable(noteMetaDataRepository.findByNoteIdAndUserId(noteId, userId)); 
		Optional<NsNotesData> noteData = noteMetaData.isPresent() ? noteDataRepository.findById(noteMetaData.get().getNoteNosqlId()) : Optional.ofNullable(null); 
		NoteVO noteVO = noteData.isPresent() ? convertNotesDataToNoteVO(noteData.get()) : null;
		return noteVO;
	}

	@Override
	public List<NoteVO> getAllNotesByUser(int userId) {

		List<NsNotesMetaData> notesMetaDataList = noteMetaDataRepository.findByUserId(userId);
  
		List<NsNotesData> notesDataList = notesMetaDataList.stream()
												.map(noteMetaData -> noteMetaData.getNoteNosqlId())
												.map(noSqlId -> noteDataRepository.findById(noSqlId))
												.filter(note -> note.isPresent())
												.map(note -> note.get())
												.collect(Collectors.toList());
		
		List<NoteVO> noteVOList = notesDataList.stream()
								  .map(noteData -> convertNotesDataToNoteVO(noteData))
								  .collect(Collectors.toList());

		return noteVOList;
	}
	
	private NoteVO convertNotesDataToNoteVO(NsNotesData noteData) {
		
		if(noteData == null) return null;
		
		NoteVO noteVO = new NoteVO();
		
		noteVO.setNoteTitle(noteData.getNoteTitle());
		noteVO.setNoteContent(noteData.getNoteContent());
		
		return noteVO;
	}
			
}
