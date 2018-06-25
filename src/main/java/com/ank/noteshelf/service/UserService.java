package com.ank.noteshelf.service;

import com.ank.noteshelf.resource.UserSignUpDetail;
import com.ank.noteshelf.vo.UserVO;

public interface UserService {

	UserVO registerUser(UserSignUpDetail userSignUpDetail);
	
}
