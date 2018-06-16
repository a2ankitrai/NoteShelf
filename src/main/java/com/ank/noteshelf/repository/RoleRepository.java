package com.ank.noteshelf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ank.noteshelf.model.NsUserRoles;

@Repository
public interface RoleRepository extends JpaRepository<NsUserRoles, Integer> {

	NsUserRoles findByUserId(int userId);
	
	List<NsUserRoles> findAllByUserId(int userId);
}
