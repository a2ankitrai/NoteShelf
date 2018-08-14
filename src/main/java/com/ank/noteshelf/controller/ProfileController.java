package com.ank.noteshelf.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ank.noteshelf.input.ProfileInput;
import com.ank.noteshelf.input.UserLoginDetail;
import com.ank.noteshelf.response.NsGenericResponse;
import com.ank.noteshelf.response.PictureResponse;
import com.ank.noteshelf.response.ProfileResponse;
import com.ank.noteshelf.service.ProfileService;

@RequestMapping("/profile")
@RestController
public class ProfileController {

	/**
	 * To support following api's
	 * 
	 * PUT /profile/deactivate
	 * */
	
	@Autowired
	ProfileService profileService;
	
	public static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@GetMapping
	public ResponseEntity<ProfileResponse> getProfileByUserId(HttpSession session){
		
		ProfileResponse profileResponse = null;
		ResponseEntity<ProfileResponse> response = null;
		
		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		profileResponse = profileService.getProfileByUserId(userLoginDetail.getUserId());
		HttpStatus status = profileResponse != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		response = new ResponseEntity<>(profileResponse, status);
		return response;
	}
	
	@PutMapping
	public ResponseEntity<ProfileResponse> updateProfileByUserId(@RequestBody @Valid ProfileInput profileInput, HttpSession session){
		
		ResponseEntity<ProfileResponse> response = null;
		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		ProfileResponse profileResponse = profileService.updateProfile(profileInput, userLoginDetail.getUserId());
		
		HttpStatus status = profileResponse != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
		response = new ResponseEntity<>(profileResponse, status);
		return response;
	}
	
	/**
	 * <code>deleteProfileByUserId</code> - Delete Profile from the user id
	 * @return ResponseEntity<Boolean>
	 * */
	@DeleteMapping
	public ResponseEntity<Boolean> deleteProfileByUserId(HttpSession session) {

		/**
		 * If the delete target doesn't exist, return 404(Not Found). 
		 * In some special case, the server take the delete request, 
		 * but will delete the resource in async way, 
		 * which means return client a 202(Accept) before the resource really get deleted.
		 * */
		
		Boolean isDeleted = null;
		ResponseEntity<Boolean> response = null;

		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		isDeleted = profileService.deleteProfileByUserId(userLoginDetail.getUserId());

		HttpStatus status = isDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		response = new ResponseEntity<>(isDeleted, status);
		return response;
	}

	@PutMapping("/picture")
	public ResponseEntity<PictureResponse> uploadProfilePicture(@RequestParam("picture") MultipartFile picture, HttpSession session) {
		logger.debug("ProfileController :: uploadProfilePicture :: start");
		
		ResponseEntity<PictureResponse> response = null;
		PictureResponse pictureResponse = null;
		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		pictureResponse = profileService.uploadProfilePicture(picture, userLoginDetail.getUserId());
		
		HttpStatus status = pictureResponse != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
		response = new ResponseEntity<>(pictureResponse, status);
		
		logger.debug("ProfileController :: uploadProfilePicture :: end");
		return response;
	}
	
	@GetMapping("/picture")
	public ResponseEntity<PictureResponse> getProfilePicture(HttpSession session) {
		ResponseEntity<PictureResponse> response = null;
		PictureResponse pictureResponse = null;
		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		pictureResponse = profileService.getProfilePictureByUserId(userLoginDetail.getUserId());
		
		HttpStatus status = pictureResponse != null ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		response = new ResponseEntity<>(pictureResponse, status);
		
		return response;
	}
	
	@DeleteMapping("/picture")
	public ResponseEntity<NsGenericResponse> deleteProfilePicture(HttpSession session) {
		
		ResponseEntity<NsGenericResponse> response = null;
		NsGenericResponse nsGenericResponse = null;
		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		nsGenericResponse = profileService.deleteProfilePicture(userLoginDetail.getUserId());
		
		HttpStatus status= nsGenericResponse != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		response = new ResponseEntity<>(nsGenericResponse, status);
		
		return response;
	}
	
}
