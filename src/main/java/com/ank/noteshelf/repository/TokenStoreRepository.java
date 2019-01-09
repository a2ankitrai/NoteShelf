package com.ank.noteshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ank.noteshelf.model.NsTokenStore;

public interface TokenStoreRepository extends JpaRepository<NsTokenStore, byte[]> {

    NsTokenStore findByTokenValue(String tokenValue);
}
