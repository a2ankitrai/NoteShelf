package com.ank.noteshelf.service.impl;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ank.noteshelf.exception.NsRuntimeException;
import com.ank.noteshelf.input.UserSignUpDetail;
import com.ank.noteshelf.mapstruct.UserObjectsMapper;
import com.ank.noteshelf.model.NsUser;
import com.ank.noteshelf.model.NsUserAuthDetail;
import com.ank.noteshelf.model.NsUserProfile;
import com.ank.noteshelf.model.NsUserRoles;
import com.ank.noteshelf.repository.RoleRepository;
import com.ank.noteshelf.repository.UserAuthDetailRepository;
import com.ank.noteshelf.repository.UserProfileRepository;
import com.ank.noteshelf.repository.UserRepository;
import com.ank.noteshelf.resource.Role;
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
    public UserResponse registerUser(UserSignUpDetail userSignUpDetail) {

	logger.debug("UserServiceImpl :: registerUser :: start ");

	if (emailExist(userSignUpDetail.getEmailAddress())) {
	    throw new NsRuntimeException("Email address already exists: " + userSignUpDetail.getEmailAddress());
	}

	if (userNameExist(userSignUpDetail.getUserName())) {
	    throw new NsRuntimeException("User Name already exists: " + userSignUpDetail.getUserName());
	}

	Date now = new Date();

	UUID userId = UuidUtils.generateRandomUuid();
	NsUser user = UserObjectsMapper.INSTANCE.mapUserSignUpToNsUser(userSignUpDetail, now, userId);
	userRepository.save(user);

	NsUserAuthDetail userAuthDetail = UserObjectsMapper.INSTANCE.mapUserSignUpToUserAuthDetail(user,
		userSignUpDetail, userId);
	userAuthDetailRepository.save(userAuthDetail);

	NsUserRoles userRole = UserObjectsMapper.INSTANCE.mapUserToUserRoles(user, Role.USER.toString(),
		UuidUtils.generateRandomUuid());
	roleRepository.save(userRole);

	NsUserProfile userProfile = UserObjectsMapper.INSTANCE.mapUserToUserProfile(user, userId);
	userProfileRepository.save(userProfile);

	UserResponse userResponse = null;
	if (user != null && userAuthDetail != null) {
	    userResponse = UserObjectsMapper.INSTANCE.mapUserToUserVO(user, userProfile, userRole, userAuthDetail);
	}

	logger.debug("UserServiceImpl :: registerUser :: end ");
	return userResponse;
    }

    private boolean emailExist(final String email) {
	return userRepository.findByEmail(email) != null;
    }

    private boolean userNameExist(final String userName) {
	return userRepository.findByUserName(userName) != null;
    }

}
