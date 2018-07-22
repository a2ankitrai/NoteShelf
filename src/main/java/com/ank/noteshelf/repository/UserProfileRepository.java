package com.ank.noteshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ank.noteshelf.model.NsUserProfile;

public interface UserProfileRepository extends JpaRepository<NsUserProfile, String> {

}
