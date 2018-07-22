package com.ank.noteshelf.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ank.noteshelf.model.NsNotesData;

@Repository
public interface NoteDataRepository extends MongoRepository<NsNotesData, String>{
	
	Optional<NsNotesData> findById(String id);
	
	void deleteById(String id);

}
