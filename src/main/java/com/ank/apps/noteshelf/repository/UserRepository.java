package com.ank.apps.noteshelf.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ank.apps.noteshelf.model.NsUser;
 
@Repository
public interface UserRepository extends CrudRepository<NsUser, String> {
	/**
	 * PagingAndSortingRepository could also have been extended for pagination extension
	 * */
	
	
	NsUser save(NsUser user);
	
//	NsUser findByUserName(String username);
	
	NsUser findByEmailAddress(String emailAddress);
	
	
}
