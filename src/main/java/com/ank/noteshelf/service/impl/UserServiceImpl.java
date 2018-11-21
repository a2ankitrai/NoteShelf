package com.ank.noteshelf.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ank.noteshelf.exception.NsRuntimeException;
import com.ank.noteshelf.input.UserRegistrationInput;
import com.ank.noteshelf.mapstruct.UserObjectsMapper;
import com.ank.noteshelf.model.NsUser;
import com.ank.noteshelf.model.NsUserAuthDetail;
import com.ank.noteshelf.model.NsUserProfile;
import com.ank.noteshelf.model.NsUserRoles;
import com.ank.noteshelf.repository.RoleRepository;
import com.ank.noteshelf.repository.UserAuthDetailRepository;
import com.ank.noteshelf.repository.UserProfileRepository;
import com.ank.noteshelf.repository.UserRepository;
import com.ank.noteshelf.resource.AuthType;
import com.ank.noteshelf.resource.Role;
import com.ank.noteshelf.resource.UserConstant;
import com.ank.noteshelf.response.UserResponse;
import com.ank.noteshelf.service.UserService;
import com.ank.noteshelf.util.UuidUtils;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthDetailRepository userAuthDetailRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    @Transactional
    public UserResponse registerUser(UserRegistrationInput userRegistrationInput) {

	logger.debug("UserServiceImpl :: registerUser :: start ");

	if (emailExist(userRegistrationInput.getEmailAddress())) {
	    throw new NsRuntimeException("Email address already exists: " + userRegistrationInput.getEmailAddress());
	}

	if (userNameExist(userRegistrationInput.getUserName())) {
	    throw new NsRuntimeException("User Name already exists: " + userRegistrationInput.getUserName());
	}

	Date now = new Date();

	UUID userId = UuidUtils.generateRandomUuid();
	NsUser user = UserObjectsMapper.INSTANCE.mapUserSignUpToNsUser(userRegistrationInput, now, userId);

	userRepository.save(user);

	NsUserAuthDetail userAuthDetail = UserObjectsMapper.INSTANCE.mapUserSignUpToUserAuthDetail(user,
		userRegistrationInput, userId);

	if (userRegistrationInput.getAuthType().equals(AuthType.APP.getValue())) {
	    userAuthDetail.setEnabled(UserConstant.N);
	}
	userAuthDetailRepository.save(userAuthDetail);

	// The below can be converted into an event published and listener mechanisms.
	NsUserRoles userRole = UserObjectsMapper.INSTANCE.mapUserToUserRoles(user, Role.USER.toString(),
		UuidUtils.generateRandomUuid());
	roleRepository.save(userRole);

	NsUserProfile userProfile = UserObjectsMapper.INSTANCE.mapUserToUserProfile(user, userId);
	
	/**
	 * temporary code for hardcoding profile name
	 * */
	userProfile.setFirstName("John");
	userProfile.setLastName("Doe");
	userProfile.setGender("M");
	userProfile.setLanguage("Hebrew");
	userProfile.setWork("Blockchain developer");
	
	userProfileRepository.save(userProfile);

	UserResponse userResponse = null;
	if (user != null && userAuthDetail != null) {
	    userResponse = UserObjectsMapper.INSTANCE.mapUserMetaDataToUserVO(user, userProfile, userRole,
		    userAuthDetail);
	}

	logger.debug("UserServiceImpl :: registerUser :: end ");
	return userResponse;
    }

    @Override
    public UserResponse getUserByUserId(byte[] userId) {

	/**
	 *  check how this DB call can be avoided. 
	 *  Store the user with active session in cache outside rest service and fetch the same from there*/
	Optional<NsUser> userOptional = userRepository.findByUserId(userId);
	UserResponse userResponse = null;

//	proper use of optional is required
//	userOptional.map(user -> {
//	    UserObjectsMapper.INSTANCE.mapUserToUserResponse(user, userResponse);
//	    return userResponse;
//	});
//	
//	userOptional.ifPresent(user -> {
//	    userResponse = new UserResponse();
//	    UserObjectsMapper.INSTANCE.mapUserToUserResponse(user, userResponse);
//	});

	userResponse = userOptional.map(user -> UserObjectsMapper.INSTANCE.mapUserToUserVO(user)).orElse(null);

//	if (userOptional.isPresent()) {
//	    NsUser user = userOptional.get();
//	    userResponse = UserObjectsMapper.INSTANCE.mapUserToUserVO(user);
//	}

	return userResponse;
    }

    private boolean emailExist(final String email) {
	return userRepository.findByEmail(email) != null;
    }

    private boolean userNameExist(final String userName) {
	return userRepository.findByUserName(userName) != null;
    }

}
