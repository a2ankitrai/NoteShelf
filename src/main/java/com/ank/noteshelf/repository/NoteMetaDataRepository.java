package com.ank.noteshelf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ank.noteshelf.model.NsNotesMetaData;

@Repository
public interface NoteMetaDataRepository extends JpaRepository<NsNotesMetaData, String>{
	
	List<NsNotesMetaData> findByUserId(int userId);
	
	NsNotesMetaData findByNoteIdAndUserId(Integer noteId, int userId);

}
