package com.ank.noteshelf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ank.ankoauth2client.resource.UserDto;
import com.ank.ankoauth2client.security.UserDetailsPrincipal;
import com.ank.noteshelf.mapstruct.UserObjectsMapper;
import com.ank.noteshelf.model.NsUser;
import com.ank.noteshelf.model.NsUserAuthDetail;
import com.ank.noteshelf.model.NsUserProfile;
import com.ank.noteshelf.model.NsUserRoles;
import com.ank.noteshelf.repository.RoleRepository;
import com.ank.noteshelf.repository.UserAuthDetailRepository;
import com.ank.noteshelf.repository.UserProfileRepository;
import com.ank.noteshelf.repository.UserRepository;

@Service
public class NsUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAuthDetailRepository userAuthDetailRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

	return loadUserByUsernameOrEmail(userName);
//	NsUser user = userRepository.findByUserName(userName);
//
//	if (user == null) {
//	    throw new UsernameNotFoundException("No User exists with credentials: " + userName);
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
    }

    public UserDetailsPrincipal loadUserByUsernameOrEmail(String userNameOrEmail) throws UsernameNotFoundException {

	NsUser user = userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail);

	if (user == null) {
	    throw new UsernameNotFoundException("No User exists with credentials: " + userNameOrEmail);
	}

	NsUserAuthDetail userAuthDetail = userAuthDetailRepository.findByUserId(user.getUserId());

	List<NsUserRoles> userRoleList = roleRepository.findAllByUserId(user.getUserId());

	NsUserProfile userProfile = userProfileRepository.findByUserId(user.getUserId()).get();

	UserDto userDto = UserObjectsMapper.INSTANCE.mapUserDataToUserDto(user, userAuthDetail, userRoleList,
		userProfile);

	UserDetailsPrincipal userDetailsPrincipal = new UserDetailsPrincipal(userDto);

	return userDetailsPrincipal;
    }

}
