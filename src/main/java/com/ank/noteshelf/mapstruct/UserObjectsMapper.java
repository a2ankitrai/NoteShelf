package com.ank.noteshelf.mapstruct;

import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ank.noteshelf.model.NsUser;
import com.ank.noteshelf.model.NsUserAuthDetail;
import com.ank.noteshelf.model.NsUserProfile;
import com.ank.noteshelf.model.NsUserRoles;
import com.ank.noteshelf.resource.UserSignUpDetail;
import com.ank.noteshelf.response.UserResponse;
import com.ank.noteshelf.util.UserConstant;

@Mapper(uses = UserObjectsMapperFunctions.class)
public interface UserObjectsMapper {

	UserObjectsMapper INSTANCE = Mappers.getMapper(UserObjectsMapper.class);

	@Mapping(source = "userSignUpDetail.userName", target = "userName")
	@Mapping(source = "userSignUpDetail.firstName", target = "firstName")
	@Mapping(source = "userSignUpDetail.lastName", target = "lastName")
	@Mapping(source = "userSignUpDetail.emailAddress", target = "email")
	@Mapping(source = "now", target = "createdDate")
	@Mapping(source = "now", target = "updatedDate")
	NsUser mapUserSignUpToNsUser(UserSignUpDetail userSignUpDetail, Date now);

	@Mapping(source = "user.userId", target = "userId")
	@Mapping(source = "user.createdDate", target = "createdDate")
	@Mapping(source = "user.updatedDate", target = "updatedDate")
	@Mapping(source = "role", target = "roleName")
	NsUserRoles mapUserToUserRoles(NsUser user, String role);

	@Mapping(source = "user.userId", target = "userId")
	@Mapping(source = "user.createdDate", target = "createdDate")
	@Mapping(source = "user.updatedDate", target = "updatedDate")
	NsUserProfile mapUserToUserProfile(NsUser user);
	
	@Mapping(source = "user.userId", target = "userId")
	@Mapping(source = "userSignUpDetail.authType", target = "authType", qualifiedByName="mapUserAuthType") 
	@Mapping(source = "userSignUpDetail.password" , target = "password", qualifiedByName="mapUserPassword")
	@Mapping(target = "accountNonLocked", constant=UserConstant.Y)
	@Mapping(target = "accountNonExpired", constant=UserConstant.Y)
	@Mapping(target = "credentialsNonExpired", constant=UserConstant.Y)
	@Mapping(target = "enabled", constant=UserConstant.Y)
	@Mapping(source = "user.createdDate", target = "createdDate")
	@Mapping(source = "user.updatedDate", target = "updatedDate")
	NsUserAuthDetail mapUserSignUpToUserAuthDetail(NsUser user, UserSignUpDetail userSignUpDetail);
	 
	@Mapping(source = "user.userId", target = "userId")
	@Mapping(source = "user.userName", target = "userName")
	@Mapping(source = "user.firstName", target = "firstName")
	@Mapping(source = "user.lastName", target = "lastName")
	@Mapping(source = "user.createdDate", target = "createdDate")
	@Mapping(source = "user.updatedDate", target = "updatedDate")
	@Mapping(source = "userProfile.userProfileId", target = "userProfileId")
	@Mapping(source = "userRole.roleName", target = "role", qualifiedByName="mapUserRole")
	@Mapping(source = "userAuthDetail.authType", target = "authType", qualifiedByName="mapUserAuthType")
	UserResponse mapUserToUserVO(NsUser user, NsUserProfile userProfile, NsUserRoles userRole, NsUserAuthDetail userAuthDetail);
	 
}
