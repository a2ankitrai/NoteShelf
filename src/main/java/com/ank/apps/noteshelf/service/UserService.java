package com.ank.apps.noteshelf.service;

import com.ank.apps.noteshelf.resource.UserDO;
import com.ank.apps.noteshelf.resource.UserSignUpDetail;


public interface UserService {

	UserDO registerUser(UserSignUpDetail userSignUpDetail);
	
}
