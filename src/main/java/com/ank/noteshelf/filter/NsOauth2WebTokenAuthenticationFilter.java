package com.ank.noteshelf.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ank.ankoauth2client.filter.AnkOauth2WebTokenAuthenticationFilter;
import com.ank.ankoauth2client.resource.UserDto;
import com.ank.noteshelf.service.impl.NsUserDetailServiceImpl;

@Component
public class NsOauth2WebTokenAuthenticationFilter extends AnkOauth2WebTokenAuthenticationFilter {

    @Autowired
    private NsUserDetailServiceImpl nsUserDetailService;

    @Override
    protected UserDto fetchUserDto(String username) {

	UserDto userDto = nsUserDetailService.loadUserByUsernameOrEmail(username).getUserDto();

	return userDto;
    }
}
