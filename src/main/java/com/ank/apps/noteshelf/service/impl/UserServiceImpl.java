package com.ank.apps.noteshelf.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ank.apps.noteshelf.exception.UserAlreadyExistException;
import com.ank.apps.noteshelf.model.NsUser;
import com.ank.apps.noteshelf.repository.UserRepository;
import com.ank.apps.noteshelf.resource.UserDO;
import com.ank.apps.noteshelf.resource.UserSignUpDetail;
import com.ank.apps.noteshelf.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	public static final Logger logger =  LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDO registerUser(UserSignUpDetail userSignUpDetail) {
		
		logger.debug("UserServiceImpl :: registerUser :: start ");
		UserDO userDO = null;
		
		
		if (emailExist(userSignUpDetail.getEmailAddress())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + userSignUpDetail.getEmailAddress());
        }
		 
		
		NsUser nsUser = new NsUser();
		nsUser.setUsername(userSignUpDetail.getUserName());
		nsUser.setFirstName(userSignUpDetail.getFirstName());
		nsUser.setLastName(userSignUpDetail.getLastName());
		nsUser.setPassword(passwordEncoder.encode(userSignUpDetail.getPassword()));
		nsUser.setEmailAddress(userSignUpDetail.getEmailAddress());
		nsUser.setLocked("N");
		nsUser.setExpired("N");
		
		nsUser = userRepository.save(nsUser);
		
		if(nsUser != null) {
			userDO = new UserDO();
			
			userDO.setUserId(nsUser.getUserId());
			userDO.setUsername(nsUser.getUsername());
			userDO.setFirstName(nsUser.getFirstName());
			userDO.setLastName(nsUser.getLastName());
			
		}
		
		logger.debug("UserServiceImpl :: registerUser :: end ");
		return userDO;
	}
	
	private boolean emailExist(final String email) {
        return userRepository.findByEmailAddress(email) != null;
    }
	
	/*
	private boolean userNameExist(final String userName) {
		return userRepository.findByUserName(userName) != null;
	}*/

}
