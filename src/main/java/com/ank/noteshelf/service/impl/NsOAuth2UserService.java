package com.ank.noteshelf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ank.ankoauth2client.resource.UserDto;
import com.ank.ankoauth2client.security.AnkOAuth2UserService;
import com.ank.ankoauth2client.security.UserDetailsPrincipal;
import com.ank.noteshelf.service.UserService;

@Service
public class NsOAuth2UserService extends AnkOAuth2UserService {

    @Autowired
    UserService userService;
    
    @Autowired
    NsUserDetailServiceImpl nsUserDetailService;

    @Override
    public UserDetailsPrincipal loadUserByEmail(String email) {
	return nsUserDetailService.loadUserByUsernameOrEmail(email);
    }
    
    @Override
    public UserDto registerNewOAuth2User(UserDto userDto) {
	return userService.registerUser(userDto);
    }
    


}
