package com.ank.noteshelf.service;

import com.ank.noteshelf.input.UserSignUpDetail;
import com.ank.noteshelf.response.UserResponse;

public interface UserService {

	UserResponse registerUser(UserSignUpDetail userSignUpDetail);
	
}
