package com.ank.noteshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ank.noteshelf.model.NsApplicationConfiguration;

@Repository
public interface ApplicationConfigRepository extends JpaRepository<NsApplicationConfiguration, byte[]> {

    NsApplicationConfiguration findByConfigurationName(String configurationName);
}
