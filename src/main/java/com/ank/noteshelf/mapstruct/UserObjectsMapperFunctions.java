package com.ank.noteshelf.mapstruct;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import com.ank.ankoauth2client.resource.AccountFlag;
import com.ank.ankoauth2client.resource.AuthType;
import com.ank.ankoauth2client.resource.UserDto;
import com.ank.noteshelf.model.NsUserAuthDetail;
import com.ank.noteshelf.model.NsUserRoles;
import com.ank.noteshelf.resource.Role;
import com.ank.noteshelf.resource.UserConstant;

public class UserObjectsMapperFunctions {

    @Named("mapUserPassword")
    public String mapUserPassword(String password) {
	if (password != null) {
	    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    return passwordEncoder.encode(password);
	}
	return null;
    }

    @Named("mapUserAuthType")
    public String mapUserAuthType(String userAuthTypeString) {
	if (userAuthTypeString == null || userAuthTypeString.trim().isEmpty()) {
	    userAuthTypeString = AuthType.NONE.getValue();
	}
	AuthType authType = AuthType.fromString(userAuthTypeString);
	return authType.toString();
    }

    @Named("mapUserRole")
    public Role mapUserRole(String roleName) {
	return Role.fromString(roleName);
    }

    @Named("mapUserRolesToUserDto")
    public List<String> mapUserRolesToUserDto(List<NsUserRoles> userRoleList) {
	return userRoleList.stream().map(userRole -> userRole.getRoleName()).collect(Collectors.toList());
    }

    @Named("mapAccountFlag")
    public AccountFlag mapAccountFlag(NsUserAuthDetail userAuthDetail) {
	AccountFlag accountFlag = new AccountFlag();
	accountFlag.setAccountNonExpired(userAuthDetail.getAccountNonExpired().equals(UserConstant.Y));
	accountFlag.setAccountNonLocked(userAuthDetail.getAccountNonLocked().equals(UserConstant.Y));
	accountFlag.setCredentialsNonExpired(userAuthDetail.getCredentialsNonExpired().equals(UserConstant.Y));
	accountFlag.setEnabled(userAuthDetail.getEnabled().equals(UserConstant.Y));
	return accountFlag;
    }

    @Named("mapUserEnabled")
    public String mapUserEnabled(UserDto userDto) {
	if (userDto.getAuthType() == AuthType.APP) {

	    return UserConstant.Y;
//	     commenting for time being. uncomment in prod.
	    // return UserConstant.N;
	} else {
	    return UserConstant.Y;
	}
    }

    @Named("mapFirstName")
    public String mapFirstName(UserDto userDto) {
	if (userDto.getFirstName() != null && !StringUtils.isEmpty(userDto.getFirstName())) {
	    return userDto.getFirstName();
	} else if (userDto.getName() != null && !StringUtils.isEmpty(userDto.getName())) {
	    return userDto.getName().split(" ")[0];
	}
	return null;
    }

    @Named("mapLastName")
    public String mapLastName(UserDto userDto) {
	if (userDto.getLastName() != null && !StringUtils.isEmpty(userDto.getLastName())) {
	    return userDto.getLastName();
	} else if (userDto.getName() != null && !StringUtils.isEmpty(userDto.getName())) {
	    int spaceIndex = userDto.getName().indexOf(' ');
	    if (spaceIndex != -1) {
		return userDto.getName().substring(spaceIndex);
	    }
	}
	return null;
    }
}
