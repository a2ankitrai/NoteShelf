package com.ank.noteshelf.service;
 
import com.ank.noteshelf.resource.ProfileInput;
import com.ank.noteshelf.response.ProfileResponse;

public interface ProfileService {
 
	ProfileResponse getProfileByUserId(int userId);
	
	ProfileResponse updateProfile(ProfileInput profileInput, int userId);
	
	Boolean deleteProfileByUserId(int userId);
}
