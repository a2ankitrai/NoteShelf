package com.ank.noteshelf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ank.noteshelf.model.NsNotesMetaData;

@Repository
public interface NoteMetaDataRepository extends JpaRepository<NsNotesMetaData, byte[]> {

    List<NsNotesMetaData> findByUserId(byte[] userId);

    NsNotesMetaData findByNoteIdAndUserId(byte[] noteId, byte[] userId);

    Integer deleteByNoteIdAndUserId(byte[] noteId, byte[] userId);

}
