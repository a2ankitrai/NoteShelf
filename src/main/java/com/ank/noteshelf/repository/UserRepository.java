package com.ank.noteshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ank.noteshelf.model.NsUser;
 
@Repository
public interface UserRepository extends JpaRepository<NsUser, String> {
	/**
	 * PagingAndSortingRepository could also have been extended for pagination extension
	 * */
	 
	NsUser findByUserName(String username);
	
	NsUser findByEmail(String email);
	
	
}
