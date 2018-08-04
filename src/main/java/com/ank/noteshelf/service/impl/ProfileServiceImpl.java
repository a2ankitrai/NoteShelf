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
import com.ank.noteshelf.response.ProfileResponse;
import com.ank.noteshelf.service.ProfileService;
import com.ank.noteshelf.util.NsMessageConstant;

@Service
public class ProfileServiceImpl implements ProfileService{

	@Autowired
	ProfileRepository profileRepository;
	
	@Override
	public ProfileResponse getProfileByUserId(int userId) {
		
		Optional<NsUserProfile> userProfile = profileRepository.findByUserId(userId);
		ProfileResponse profileResponse = null;
		
		if(userProfile.isPresent()) {
			profileResponse = ProfileObjectsMapper.INSTANCE.mapUserProfileToProfileVO(userProfile.get());
		}
		else {
			throw new EmptyResultDataAccessException(NsMessageConstant.NO_PROFILE_FOUND_BY_ID,1);
		}
		return profileResponse;
	}

	@Override
	@Transactional
	public ProfileResponse updateProfile(ProfileInput profileInput, int userId) {
		
		ProfileResponse profileResponse = null;
		NsUserProfile userProfile = null;
		Optional<NsUserProfile> userProfileOptional = profileRepository.findByUserId(userId);

		if(userProfileOptional.isPresent()) { 
			userProfile = ProfileObjectsMapper.INSTANCE.mapProfileInputAndExistingProfileToUserProfile(profileInput, userProfileOptional.get());
			userProfile.setUpdatedDate(new Date());
			userProfile = profileRepository.save(userProfile);
		} 
		else {
			throw new EmptyResultDataAccessException(NsMessageConstant.NO_PROFILE_FOUND_BY_ID,1);
		}
		profileResponse = ProfileObjectsMapper.INSTANCE.mapUserProfileToProfileVO(userProfile);
		return profileResponse;
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
