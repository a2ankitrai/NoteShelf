package com.ank.noteshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ank.noteshelf.model.NsUserAuthDetail;

@Repository
public interface UserAuthDetailRepository extends JpaRepository<NsUserAuthDetail, String> {

	NsUserAuthDetail findByUserId(byte[] userId);
}
