package com.ank.apps.noteshelf.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ank.apps.noteshelf.model.NsUserAuthDetail;

@Repository
public interface UserAuthDetailRepository extends CrudRepository<NsUserAuthDetail, String> {

}
