package com.ank.noteshelf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ank.noteshelf.model.NsUserProfile;

@Repository
public interface ProfileRepository extends JpaRepository<NsUserProfile, String> {

    Optional<NsUserProfile> findByUserId(byte[] userId);

}
