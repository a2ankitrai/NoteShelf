package com.ank.noteshelf.service;

import com.ank.noteshelf.resource.UserDO;
import com.ank.noteshelf.resource.UserSignUpDetail;

public interface UserService {

	UserDO registerUser(UserSignUpDetail userSignUpDetail);
	
}
