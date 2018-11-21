package com.ank.noteshelf.mapstruct;

import java.util.Date;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ank.noteshelf.input.UserRegistrationInput;
import com.ank.noteshelf.model.NsUser;
import com.ank.noteshelf.model.NsUserAuthDetail;
import com.ank.noteshelf.model.NsUserProfile;
import com.ank.noteshelf.model.NsUserRoles;
import com.ank.noteshelf.resource.UserConstant;
import com.ank.noteshelf.response.UserResponse;

@Mapper(uses = { UserObjectsMapperFunctions.class, NsCommonMapperFunctions.class })
public interface UserObjectsMapper {

    UserObjectsMapper INSTANCE = Mappers.getMapper(UserObjectsMapper.class);

    @Mapping(source = "userId", target = "userId", qualifiedByName = "mappUuidToByte")
    @Mapping(source = "userSignUpDetail.userName", target = "userName")
//    @Mapping(source = "userSignUpDetail.firstName", target = "firstName")
//    @Mapping(source = "userSignUpDetail.lastName", target = "lastName")
    @Mapping(source = "userSignUpDetail.emailAddress", target = "email")
    @Mapping(source = "now", target = "createdDate")
    @Mapping(source = "now", target = "updatedDate")
    NsUser mapUserSignUpToNsUser(UserRegistrationInput userSignUpDetail, Date now, UUID userId);

    @Mapping(source = "roleId", target = "roleId", qualifiedByName = "mappUuidToByte")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.createdDate", target = "createdDate")
    @Mapping(source = "user.updatedDate", target = "updatedDate")
    @Mapping(source = "role", target = "roleName")
    NsUserRoles mapUserToUserRoles(NsUser user, String role, UUID roleId);

    @Mapping(source = "userProfileId", target = "userProfileId", qualifiedByName = "mappUuidToByte")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.createdDate", target = "createdDate")
    @Mapping(source = "user.updatedDate", target = "updatedDate")
    NsUserProfile mapUserToUserProfile(NsUser user, UUID userProfileId);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "userSignUpDetail.authType", target = "authType", qualifiedByName = "mapUserAuthType")
    @Mapping(source = "userSignUpDetail.password", target = "password", qualifiedByName = "mapUserPassword")
    @Mapping(target = "accountNonLocked", constant = UserConstant.Y)
    @Mapping(target = "accountNonExpired", constant = UserConstant.Y)
    @Mapping(target = "credentialsNonExpired", constant = UserConstant.Y)
    @Mapping(target = "enabled", constant = UserConstant.Y)
    @Mapping(source = "user.createdDate", target = "createdDate")
    @Mapping(source = "user.updatedDate", target = "updatedDate")
    @Mapping(source = "userAuthDetailId", target = "userAuthDetailId", qualifiedByName = "mappUuidToByte")
    NsUserAuthDetail mapUserSignUpToUserAuthDetail(NsUser user, UserRegistrationInput userSignUpDetail,
	    UUID userAuthDetailId);

    @Mapping(source = "user.userId", target = "userId", qualifiedByName = "mapByteToUuid")
    @Mapping(source = "user.userName", target = "userName")
//    @Mapping(source = "user.firstName", target = "firstName")
//    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.createdDate", target = "createdDate")
    @Mapping(source = "user.updatedDate", target = "updatedDate")
    @Mapping(source = "userProfile.userProfileId", target = "userProfileId", qualifiedByName = "mapByteToUuid")
    @Mapping(source = "userRole.roleName", target = "role", qualifiedByName = "mapUserRole")
    @Mapping(source = "userAuthDetail.authType", target = "authType", qualifiedByName = "mapUserAuthType")
    UserResponse mapUserMetaDataToUserVO(NsUser user, NsUserProfile userProfile, NsUserRoles userRole,
	    NsUserAuthDetail userAuthDetail);

    @Mapping(source = "user.userId", target = "userId", qualifiedByName = "mapByteToUuid")
    @Mapping(source = "user.userName", target = "userName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.createdDate", target = "createdDate")
    @Mapping(source = "user.updatedDate", target = "updatedDate")
    UserResponse mapUserToUserVO(NsUser user);

//    check how to generate mappings with void return type with 2 arguments.
//    @Mapping(source = "user.userId", target = "userResponse.userId", qualifiedByName = "mapByteToUuid")
//    @Mapping(source = "user.userName", target = "userResponse.userName")
//    @Mapping(source = "user.email", target = "userResponse.email")
//    @Mapping(source = "user.createdDate", target = "userResponse.createdDate")
//    @Mapping(source = "user.updatedDate", target = "userResponse.updatedDate")
//    void mapUserToUserResponse(NsUser user, UserResponse userResponse);

}
