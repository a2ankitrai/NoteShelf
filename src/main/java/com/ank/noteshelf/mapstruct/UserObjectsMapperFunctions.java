package com.ank.noteshelf.mapstruct;

import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ank.noteshelf.resource.AuthType;
import com.ank.noteshelf.resource.Role;

public class UserObjectsMapperFunctions {

	@Named("mapUserPassword")
	public String mapUserPassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	@Named("mapUserAuthType")
	public String mapUserAuthType(String userAuthTypeString) {
		AuthType authType = AuthType.fromString(userAuthTypeString);
		return authType.toString();
	}
	
	@Named("mapUserRole")
	public Role mapUserRole(String roleName) {
		return Role.fromString(roleName);
	}
	
	
}
