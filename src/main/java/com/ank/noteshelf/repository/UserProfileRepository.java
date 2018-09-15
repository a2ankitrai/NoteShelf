package com.ank.noteshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ank.noteshelf.model.NsUserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<NsUserProfile, String> {

}
