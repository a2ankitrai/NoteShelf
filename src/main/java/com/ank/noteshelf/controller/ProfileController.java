package com.ank.noteshelf.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.ank.noteshelf.response.NsGenericResponse;
import com.ank.noteshelf.response.PictureResponse;
import com.ank.noteshelf.response.ProfileResponse;
import com.ank.noteshelf.service.ProfileService;
import com.ank.noteshelf.util.UserDetailUtil;

@RequestMapping("/profile")
@RestController
public class ProfileController {

    /**
     * To support following api's
     * 
     * PUT /profile/deactivate
     */

    @Autowired
    ProfileService profileService;

    public static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @GetMapping
    public ResponseEntity<ProfileResponse> getUserProfile(HttpSession session) {

	ProfileResponse profileResponse = null;
	ResponseEntity<ProfileResponse> response = null;

	profileResponse = profileService.getProfileByUserId(UserDetailUtil.getUserIdFromSession(session));
	HttpStatus status = profileResponse != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
	response = new ResponseEntity<>(profileResponse, status);
	return response;
    }

    /**
     * <code>updateProfileByUserId</code> - Update User Profile from ProfileInput
     * 
     * @return ResponseEntity<ProfileResponse>
     */
    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfileByUserId(@RequestBody @Valid ProfileInput profileInput,
	    HttpSession session) {

	ResponseEntity<ProfileResponse> response = null;
	ProfileResponse profileResponse = profileService.updateProfile(profileInput,
		UserDetailUtil.getUserIdFromSession(session));

	HttpStatus status = profileResponse != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
	response = new ResponseEntity<>(profileResponse, status);
	return response;
    }

    /**
     * <code>deleteProfileByUserId</code> - Delete Profile from the user id
     * 
     * @return ResponseEntity<Boolean>
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteProfileByUserId(HttpSession session) {

	/**
	 * If the delete target doesn't exist, return 404(Not Found). In some special
	 * case, the server take the delete request, but will delete the resource in
	 * async way, which means return client a 202(Accept) before the resource really
	 * get deleted.
	 */

	Boolean isDeleted = null;
	ResponseEntity<Boolean> response = null;

	isDeleted = profileService.deleteProfileByUserId(UserDetailUtil.getUserIdFromSession(session));

	HttpStatus status = isDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;
	response = new ResponseEntity<>(isDeleted, status);
	return response;
    }

    @GetMapping("/picture")
    public ResponseEntity<Resource> getProfilePicture(HttpSession session) {
	PictureResponse pictureResponse = null;
	pictureResponse = profileService.getProfilePictureByUserId(UserDetailUtil.getUserIdFromSession(session));

	return ResponseEntity.ok().contentType(MediaType.parseMediaType(pictureResponse.getContentType()))
		.header(HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=\"" + pictureResponse.getPictureName() + "\"")
		.body(pictureResponse.getPictureResource());
    }

    @PutMapping("/picture")
    public ResponseEntity<PictureResponse> updateProfilePicture(@RequestParam("picture") MultipartFile picture,
	    HttpSession session) {

	ResponseEntity<PictureResponse> response = null;
	PictureResponse pictureResponse = null;
	pictureResponse = profileService.updateProfilePicture(picture, UserDetailUtil.getUserIdFromSession(session));

	HttpStatus status = pictureResponse != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
	response = new ResponseEntity<>(pictureResponse, status);
	return response;
    }

    @DeleteMapping("/picture")
    public ResponseEntity<NsGenericResponse> deleteProfilePicture(HttpSession session) {

	ResponseEntity<NsGenericResponse> response = null;
	NsGenericResponse nsGenericResponse = null;
	nsGenericResponse = profileService.deleteProfilePicture(UserDetailUtil.getUserIdFromSession(session));

	HttpStatus status = nsGenericResponse != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
	response = new ResponseEntity<>(nsGenericResponse, status);

	return response;
    }

}
