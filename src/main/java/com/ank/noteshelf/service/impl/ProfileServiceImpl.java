package com.ank.noteshelf.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ank.noteshelf.input.ProfileInput;
import com.ank.noteshelf.mapstruct.ProfileObjectsMapper;
import com.ank.noteshelf.model.NsUserProfile;
import com.ank.noteshelf.repository.ProfileRepository;
import com.ank.noteshelf.resource.NsMessageConstant;
import com.ank.noteshelf.response.NsGenericResponse;
import com.ank.noteshelf.response.PictureResponse;
import com.ank.noteshelf.response.ProfileResponse;
import com.ank.noteshelf.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService{

	@Autowired
	ProfileRepository profileRepository;
	
	@Override
	public ProfileResponse getProfileByUserId(byte[] userId) {
		
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
	public ProfileResponse updateProfile(ProfileInput profileInput, byte[] userId) {
		
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
	public Boolean deleteProfileByUserId(byte[] userId) {
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

	@Override
	public PictureResponse uploadProfilePicture(MultipartFile picture, byte[] userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NsGenericResponse deleteProfilePicture(byte[] userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PictureResponse getProfilePictureByUserId(byte[] userId) {
		// TODO Auto-generated method stub
		return null;
	}
 
 
}
