package com.ank.noteshelf.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ank.noteshelf.mapstruct.ProfileObjectsMapper;
import com.ank.noteshelf.model.NsUserProfile;
import com.ank.noteshelf.repository.ProfileRepository;
import com.ank.noteshelf.resource.ProfileInput;
import com.ank.noteshelf.service.ProfileService;
import com.ank.noteshelf.util.NsMessageConstant;
import com.ank.noteshelf.vo.ProfileVO;

@Service
public class ProfileServiceImpl implements ProfileService{

	@Autowired
	ProfileRepository profileRepository;
	
	@Override
	public ProfileVO getProfileByUserId(int userId) {
		
		Optional<NsUserProfile> userProfile = profileRepository.findByUserId(userId);
		ProfileVO profileVo = null;
		
		if(userProfile.isPresent()) {
			profileVo = ProfileObjectsMapper.INSTANCE.mapUserProfileToProfileVO(userProfile.get());
		}
		else {
			throw new EmptyResultDataAccessException(NsMessageConstant.NO_PROFILE_FOUND_BY_ID,1);
		}
		return profileVo;
	}

	@Override
	@Transactional
	public ProfileVO updateProfile(ProfileInput profileInput, int userId) {
		
		ProfileVO profileVo = null;
		NsUserProfile userProfile = null;
		Optional<NsUserProfile> userProfileOptional = profileRepository.findByUserId(userId);

		if(userProfileOptional.isPresent()) { 
			userProfile = ProfileObjectsMapper.INSTANCE.mapProfileInputAndUserIdToUserProfile(profileInput, userProfileOptional.get());
			userProfile.setUpdatedDate(new Date());
			userProfile = profileRepository.save(userProfile);
		} 
		profileVo = ProfileObjectsMapper.INSTANCE.mapUserProfileToProfileVO(userProfile);
		return profileVo;
	}

	@Override
	public Boolean deleteProfileByUserId(int userId) {
		Boolean isDeleted = false;
		Optional<NsUserProfile> userProfileOptional = profileRepository.findByUserId(userId);
		
		if(userProfileOptional.isPresent()) {
			profileRepository.delete(userProfileOptional.get());
			isDeleted = true;
		}
		else {
			throw new EmptyResultDataAccessException(NsMessageConstant.NO_PROFILE_FOUND_BY_ID,1);
		}
		return isDeleted;
	}

	
	
}
