package com.ank.noteshelf.service;
 
import com.ank.noteshelf.resource.ProfileInput;
import com.ank.noteshelf.vo.ProfileVO;

public interface ProfileService {
 
	ProfileVO getProfileByUserId(int userId);
	
	ProfileVO updateProfile(ProfileInput profileInput, int userId);
	
	Boolean deleteProfileByUserId(int userId);
}
