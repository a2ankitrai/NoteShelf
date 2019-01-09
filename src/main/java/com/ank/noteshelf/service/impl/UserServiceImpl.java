package com.ank.noteshelf.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ank.ankoauth2client.resource.AuthType;
import com.ank.ankoauth2client.resource.UserDto;
import com.ank.noteshelf.event.UserEventPublisher;
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
import com.ank.noteshelf.resource.Role;
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
    UserProfileRepository userProfileRepository;

    @Autowired
    UserEventPublisher userEventPublisher;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto registerAppUser(UserRegistrationInput userRegistrationInput) {

	UserDto userDto = new UserDto();

	userDto.setUserName(userRegistrationInput.getUserName());
	userDto.setEmail(userRegistrationInput.getEmailAddress());
	userDto.setPassword(userRegistrationInput.getPassword());
	userDto.setAuthType(AuthType.APP);

	return registerUser(userDto);

    }

    @Transactional
    public UserDto registerUser(UserDto userDto) {

	if (emailExist(userDto.getEmail())) {
	    throw new NsRuntimeException("Email address already exists: " + userDto.getEmail());
	}

	if (userNameExist(userDto.getUserName())) {
	    throw new NsRuntimeException("User Name already exists: " + userDto.getUserName());
	}

	Date now = new Date();
	UUID userId = UuidUtils.generateRandomUuid();

	NsUser user = UserObjectsMapper.INSTANCE.mapUserDtoToNsUser(userDto, now, userId);
	userRepository.save(user);
	userDto.setUserId(user.getUserId());

	NsUserAuthDetail userAuthDetail = UserObjectsMapper.INSTANCE.mapUserDtoToNsUserAuthDetail(user, userDto);
	userAuthDetailRepository.save(userAuthDetail);

	NsUserRoles userRole = UserObjectsMapper.INSTANCE.mapUserToUserRoles(user, Role.USER.toString(),
		UuidUtils.generateRandomUuid());
	roleRepository.save(userRole);
	userDto.setRoles(Arrays.asList(userRole.getRoleName()));

	NsUserProfile userProfile = UserObjectsMapper.INSTANCE.mapUserDtoToUserProfile(user, userDto);
	userProfileRepository.save(userProfile);

	userEventPublisher.publishUserRegistrationEmailEvent(userDto);

	return userDto;
    }

    @Override
    public UserDto resetPassword(String userEmail, String password) {

	NsUser user = userRepository.findByEmail(userEmail);
	UserDto userDto = null;

	if (user != null) {
	    NsUserAuthDetail userAuthDetail = userAuthDetailRepository.findByUserId(user.getUserId());
	    userAuthDetail.setPassword(passwordEncoder.encode(password));
	    userAuthDetailRepository.save(userAuthDetail);

	    userDto = UserObjectsMapper.INSTANCE.mapUserToUserDto(user);
	}

	return userDto;
    }

    @Override
    public UserDto getUserByUserId(byte[] userId) {

	/**
	 * check how this DB call can be avoided. Store the user with active session in
	 * cache outside rest service and fetch the same from there
	 */
	Optional<NsUser> userOptional = userRepository.findByUserId(userId);
	UserDto userDto = userOptional.map(user -> UserObjectsMapper.INSTANCE.mapUserToUserDto(user)).orElse(null);

	return userDto;
    }

    public UserDto getUserByEmail(String email) {

	Optional<NsUser> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
	UserDto userDto = userOptional.map(user -> UserObjectsMapper.INSTANCE.mapUserToUserDto(user)).orElse(null);

	if (userDto != null) {
	    NsUserAuthDetail userAuthDetail = userAuthDetailRepository.findByUserId(userDto.getUserId());
	    userDto.setAuthType(AuthType.valueOf(userAuthDetail.getAuthType()));
	}

	return userDto;
    }

//    public UserDto getUserByUserID(byte[] userId) {
//
//	Optional<NsUser> user = userRepository.findByUserId(userId);
//
//	if (user == null) {
//	    throw new UsernameNotFoundException("No User exists with credentials: " + userNameOrEmail);
//	}
//
//	NsUserAuthDetail userAuthDetail = userAuthDetailRepository.findByUserId(user.getUserId());
//
//	List<NsUserRoles> userRoleList = roleRepository.findAllByUserId(user.getUserId());
//
//	NsUserProfile userProfile = userProfileRepository.findByUserId(user.getUserId()).get();
//
//	UserDto userDto = UserObjectsMapper.INSTANCE.mapUserDataToUserDto(user, userAuthDetail, userRoleList,
//		userProfile);
//
//	UserDetailsPrincipal userDetailsPrincipal = new UserDetailsPrincipal(userDto);
//
//	return userDetailsPrincipal;
//
//    }

    private boolean emailExist(final String email) {
	return userRepository.findByEmail(email) != null;
    }

    private boolean userNameExist(final String userName) {
	return userRepository.findByUserName(userName) != null;
    }

}
