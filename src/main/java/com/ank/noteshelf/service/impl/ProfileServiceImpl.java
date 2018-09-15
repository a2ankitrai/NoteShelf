package com.ank.noteshelf.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ank.noteshelf.exception.NsRuntimeException;
import com.ank.noteshelf.input.ProfileInput;
import com.ank.noteshelf.mapstruct.ProfileObjectsMapper;
import com.ank.noteshelf.model.NsUserProfile;
import com.ank.noteshelf.repository.ProfileRepository;
import com.ank.noteshelf.resource.NsMessageConstant;
import com.ank.noteshelf.response.NsGenericResponse;
import com.ank.noteshelf.response.PictureResponse;
import com.ank.noteshelf.response.ProfileResponse;
import com.ank.noteshelf.service.FileStorageService;
import com.ank.noteshelf.service.ProfileService;
import com.google.cloud.storage.Blob;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    @Qualifier("googleCloudStorageService")
    FileStorageService googleFileStorageService;

    @Override
    public ProfileResponse getProfileByUserId(byte[] userId) {

	Optional<NsUserProfile> userProfile = profileRepository.findByUserId(userId);
	ProfileResponse profileResponse = null;

	if (userProfile.isPresent()) {
	    profileResponse = ProfileObjectsMapper.INSTANCE.mapUserProfileToProfileVO(userProfile.get());
	} else {
	    throw new EmptyResultDataAccessException(NsMessageConstant.NO_PROFILE_FOUND_BY_ID, 1);
	}
	return profileResponse;
    }

    @Override
    @Transactional
    public ProfileResponse updateProfile(ProfileInput profileInput, byte[] userId) {

	ProfileResponse profileResponse = null;
	NsUserProfile userProfile = null;
	Optional<NsUserProfile> userProfileOptional = profileRepository.findByUserId(userId);

	if (userProfileOptional.isPresent()) {
	    userProfile = ProfileObjectsMapper.INSTANCE.mapProfileInputAndExistingProfileToUserProfile(profileInput,
		    userProfileOptional.get());
	    userProfile.setUpdatedDate(new Date());
	    userProfile = profileRepository.save(userProfile);
	} else {
	    throw new EmptyResultDataAccessException(NsMessageConstant.NO_PROFILE_FOUND_BY_ID, 1);
	}
	profileResponse = ProfileObjectsMapper.INSTANCE.mapUserProfileToProfileVO(userProfile);
	return profileResponse;
    }

    @Override
    public Boolean deleteProfileByUserId(byte[] userId) {
	Boolean isDeleted = false;
	Optional<NsUserProfile> userProfileOptional = profileRepository.findByUserId(userId);

	if (userProfileOptional.isPresent()) {
	    profileRepository.delete(userProfileOptional.get());
	    isDeleted = true;
	} else {
	    throw new EmptyResultDataAccessException(NsMessageConstant.NO_PROFILE_FOUND_BY_ID, 1);
	}
	return isDeleted;
    }

    @Override
    @Transactional
    public PictureResponse updateProfilePicture(MultipartFile picture, byte[] userId) {

	NsUserProfile userProfile = null;
	Optional<NsUserProfile> userProfileOptional = profileRepository.findByUserId(userId);

	if (!userProfileOptional.isPresent())
	    throw new EmptyResultDataAccessException(NsMessageConstant.NO_PROFILE_FOUND_BY_ID, 1);

	userProfile = userProfileOptional.get();
	String existingProfileImage = userProfile.getProfileImage();

	PictureResponse pictureUploadResponse = null;
	try {
	    pictureUploadResponse = googleFileStorageService.saveFile(picture);
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new NsRuntimeException("Could not store the file. Please try again", e);
	}

	if (pictureUploadResponse != null) {
	    userProfile.setProfileImage(pictureUploadResponse.getPictureName());
	    userProfile.setUpdatedDate(new Date());
	    userProfile = profileRepository.save(userProfile);
	}

	if (existingProfileImage != null && !existingProfileImage.isEmpty()) {
	    googleFileStorageService.deleteFile(existingProfileImage);
	}

	return pictureUploadResponse;
    }

    @Override
    public NsGenericResponse deleteProfilePicture(byte[] userId) {

	NsUserProfile userProfile = null;
	Optional<NsUserProfile> userProfileOptional = profileRepository.findByUserId(userId);

	if (!userProfileOptional.isPresent())
	    throw new EmptyResultDataAccessException(NsMessageConstant.NO_PROFILE_FOUND_BY_ID, 1);

	userProfile = userProfileOptional.get();
	String profileImageName = userProfile.getProfileImage();
	boolean isDeleted = googleFileStorageService.deleteFile(profileImageName);

	NsGenericResponse response = new NsGenericResponse();
	if (isDeleted) {
	    response.setMessage(NsMessageConstant.PICTURE_DELETE_SUCCESS);
	    userProfile.setProfileImage(null);
	    profileRepository.save(userProfile);
	} else {
	    response.setMessage(NsMessageConstant.PICTURE_DELETE_ERROR_NOT_FOUND);
	}
	response.setTimeStamp(new Date());

	return response;
    }

    @Override
    public PictureResponse getProfilePictureByUserId(byte[] userId) {

	NsUserProfile userProfile = null;
	Optional<NsUserProfile> userProfileOptional = profileRepository.findByUserId(userId);
	PictureResponse pictureResponse = null;

	if (!userProfileOptional.isPresent())
	    throw new EmptyResultDataAccessException(NsMessageConstant.NO_PROFILE_FOUND_BY_ID, 1);

	userProfile = userProfileOptional.get();

	Blob blob = googleFileStorageService.getFile(userProfile.getProfileImage());

	if (blob != null) {
	    pictureResponse = new PictureResponse();
	    pictureResponse.setPictureResource(new ByteArrayResource(blob.getContent()));
	    pictureResponse.setContentType(blob.getContentType());
	    pictureResponse.setSize(blob.getSize());
	    pictureResponse.setDownloadUri(blob.getMediaLink());
	}
	else { 
	    throw new EmptyResultDataAccessException(NsMessageConstant.NO_PICTURE_FOUND_FOR_USER, 1);
	}
	 

	return pictureResponse;
    }

}
