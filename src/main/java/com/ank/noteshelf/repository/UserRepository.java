package com.ank.noteshelf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ank.noteshelf.model.NsUser;

@Repository
public interface UserRepository extends JpaRepository<NsUser, byte[]> {
    /**
     * PagingAndSortingRepository could also have been extended for pagination
     * extension
     */

    NsUser findByUserName(String username);

    NsUser findByEmail(String email);

    Optional<NsUser> findByUserId(byte[] userId);

}
