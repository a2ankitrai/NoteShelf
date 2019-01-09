package com.ank.noteshelf.util;

import static com.ank.noteshelf.resource.NsCommonConstant.USER_DETAILS_PRINCIPAL;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ank.ankoauth2client.resource.UserDto;
import com.ank.ankoauth2client.security.UserDetailsPrincipal;

public class UserDetailUtil {

    public static UserDto getUserDtoFromSession(HttpSession session) {
	UserDetailsPrincipal userDetailPrincipal = (UserDetailsPrincipal) session.getAttribute(USER_DETAILS_PRINCIPAL);

	/**
	 * Check how this will work in case of multiple users...
	 * */
	if (userDetailPrincipal == null) {
	    userDetailPrincipal = (UserDetailsPrincipal) SecurityContextHolder.getContext().getAuthentication()
		    .getPrincipal();
	}
	return userDetailPrincipal.getUserDto();
    }

    public static byte[] getUserIdFromSession(HttpSession session) {

	return getUserDtoFromSession(session).getUserId();
    }

    public static void setUserDetailsPrincipalIntoSession(HttpSession session) {
	UserDetailsPrincipal userDetailPrincipal = (UserDetailsPrincipal) SecurityContextHolder.getContext()
		.getAuthentication().getPrincipal();
	session.setAttribute(USER_DETAILS_PRINCIPAL, userDetailPrincipal);
    }

}
