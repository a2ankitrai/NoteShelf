package com.ank.noteshelf.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ank.noteshelf.exception.UserAlreadyExistException;
import com.ank.noteshelf.model.NsUser;
import com.ank.noteshelf.model.NsUserAuthDetail;
import com.ank.noteshelf.model.NsUserRoles;
import com.ank.noteshelf.repository.RoleRepository;
import com.ank.noteshelf.repository.UserAuthDetailRepository;
import com.ank.noteshelf.repository.UserRepository;
import com.ank.noteshelf.resource.AuthType;
import com.ank.noteshelf.resource.Role;
import com.ank.noteshelf.resource.UserDO;
import com.ank.noteshelf.resource.UserSignUpDetail;
import com.ank.noteshelf.service.UserService;
import com.ank.noteshelf.util.UserConstant;

@Service
public class UserServiceImpl implements UserService {

	public static final Logger logger =  LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserAuthDetailRepository userAuthDetailRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public UserDO registerUser(UserSignUpDetail userSignUpDetail) {
		
		logger.debug("UserServiceImpl :: registerUser :: start ");
		
		if (emailExist(userSignUpDetail.getEmailAddress())) {
            throw new UserAlreadyExistException("Email address already exists: " + userSignUpDetail.getEmailAddress());
        }
		 
		if (userNameExist(userSignUpDetail.getUserName())) {
            throw new UserAlreadyExistException("User Name already exists: " + userSignUpDetail.getUserName());
        }
		
		NsUser user = new NsUser();
		 
		user.setUserName(userSignUpDetail.getUserName());
		user.setFirstName(userSignUpDetail.getFirstName());
		user.setLastName(userSignUpDetail.getLastName());
		user.setEmail(userSignUpDetail.getEmailAddress());
		
		Date now = new Date();
		user.setCreatedDate(now);
		user.setUpdatedDate(now);
		
		user = userRepository.save(user);
		  
		NsUserAuthDetail userAuthDetail = new NsUserAuthDetail(user.getUserId());
		
		AuthType authType = AuthType.fromString(userSignUpDetail.getAuthType());
		userAuthDetail.setAuthType(authType.toString());
		
		if(authType == AuthType.APP) {
			userAuthDetail.setPassword(passwordEncoder.encode(userSignUpDetail.getPassword()));
		}
		userAuthDetail.setAccountNonExpired(UserConstant.Y);
		userAuthDetail.setAccountNonLocked(UserConstant.Y);
		userAuthDetail.setCredentialsNonExpired(UserConstant.Y);
		userAuthDetail.setEnabled(UserConstant.Y);
		userAuthDetail.setCreatedDate(now);
		userAuthDetail.setUpdatedDate(now);
		userAuthDetail = userAuthDetailRepository.save(userAuthDetail);
		
		NsUserRoles userRole = new NsUserRoles(user.getUserId(),Role.USER.toString());
		userRole.setCreatedDate(now);
		userRole.setUpdatedDate(now);
		userRole = roleRepository.save(userRole);
		 	
		UserDO userDO = null;
		
		if(user != null && userAuthDetail != null) {
			
			userDO = new UserDO();
			userDO.setUserId(user.getUserId());
			userDO.setUsername(user.getUserName());
			userDO.setFirstName(user.getFirstName());
			userDO.setLastName(user.getLastName());
			
			userDO.setRole(Role.fromString(userRole.getRoleName()));
			
			userDO.setAuthType(AuthType.fromString(userAuthDetail.getAuthType()));
			userDO.setCreatedDate(user.getCreatedDate());
			userDO.setUpdatedDate(user.getUpdatedDate());
			
		}
		
		logger.debug("UserServiceImpl :: registerUser :: end ");
		return userDO;
	}
	
	private boolean emailExist(final String email) {
        return userRepository.findByEmail(email) != null;
    }
	
	 
	private boolean userNameExist(final String userName) {
		return userRepository.findByUserName(userName) != null;
	}

}
