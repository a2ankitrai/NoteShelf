package com.ank.noteshelf.mapstruct;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ank.ankoauth2client.resource.UserDto;
import com.ank.noteshelf.input.UserRegistrationInput;
import com.ank.noteshelf.model.NsUser;
import com.ank.noteshelf.model.NsUserAuthDetail;
import com.ank.noteshelf.model.NsUserProfile;
import com.ank.noteshelf.model.NsUserRoles;
import com.ank.noteshelf.resource.UserConstant;

@Mapper(uses = { UserObjectsMapperFunctions.class, NsCommonMapperFunctions.class })
public interface UserObjectsMapper {

    UserObjectsMapper INSTANCE = Mappers.getMapper(UserObjectsMapper.class);

    @Mapping(source = "userId", target = "userId", qualifiedByName = "mappUuidToByte")
    @Mapping(source = "userSignUpDetail.userName", target = "userName")
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

    @Mapping(source = "userDto.userId", target = "userProfileId")
    @Mapping(source = "userDto.userId", target = "userId")
    @Mapping(source = "user.createdDate", target = "createdDate")
    @Mapping(source = "user.updatedDate", target = "updatedDate")
    @Mapping(source = "userDto", target = "firstName", qualifiedByName = "mapFirstName")
    @Mapping(source = "userDto", target = "lastName", qualifiedByName = "mapLastName")
    @Mapping(source = "userDto.profilePicture", target = "profileImage")
    NsUserProfile mapUserDtoToUserProfile(NsUser user, UserDto userDto);
    
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "userSignUpDetail.authType", target = "authType", qualifiedByName = "mapUserAuthType")
    @Mapping(source = "userSignUpDetail.password", target = "password", qualifiedByName = "mapUserPassword")
    @Mapping(target = "accountNonLocked", constant = UserConstant.Y)
    @Mapping(target = "accountNonExpired", constant = UserConstant.Y)
    @Mapping(target = "credentialsNonExpired", constant = UserConstant.Y)
    @Mapping(target = "enabled", constant = UserConstant.N)
    @Mapping(source = "user.createdDate", target = "createdDate")
    @Mapping(source = "user.updatedDate", target = "updatedDate")
    @Mapping(source = "userAuthDetailId", target = "userAuthDetailId", qualifiedByName = "mappUuidToByte")
    NsUserAuthDetail mapUserSignUpToUserAuthDetail(NsUser user, UserRegistrationInput userSignUpDetail,
	    UUID userAuthDetailId);

//    @Mapping(source = "user.userId", target = "userId", qualifiedByName = "mapByteToUuid")
//    @Mapping(source = "user.userName", target = "userName")
//    @Mapping(source = "user.createdDate", target = "createdDate")
//    @Mapping(source = "user.updatedDate", target = "updatedDate")
//    @Mapping(source = "userProfile.userProfileId", target = "userProfileId", qualifiedByName = "mapByteToUuid")
//    @Mapping(source = "userRole.roleName", target = "role", qualifiedByName = "mapUserRole")
//    @Mapping(source = "userAuthDetail.authType", target = "authType", qualifiedByName = "mapUserAuthType")
//    UserDto mapUserMetaDataToUserVO(NsUser user, NsUserProfile userProfile, NsUserRoles userRole,
//	    NsUserAuthDetail userAuthDetail);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.userName", target = "userName")
    @Mapping(source = "user.email", target = "email")
//    @Mapping(source = "user.createdDate", target = "createdDate")
//    @Mapping(source = "user.updatedDate", target = "updatedDate")
    UserDto mapUserToUserDto(NsUser user);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.userName", target = "userName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "userAuthDetail.authType", target = "authType", qualifiedByName = "mapUserAuthType")
    @Mapping(source = "userRoleList", target = "roles", qualifiedByName = "mapUserRoles")
    @Mapping(source = "userAuthDetail", target = "accountFlag", qualifiedByName = "mapAccountFlag")
    @Mapping(source = "userProfile.profileImage", target = "profilePicture")
    @Mapping(source = "userProfile.firstName", target = "firstName")
    @Mapping(source = "userProfile.lastName", target = "lastName")
    UserDto mapUserDataToUserDto(NsUser user, NsUserAuthDetail userAuthDetail, List<NsUserRoles> userRoleList,
	    NsUserProfile userProfile);

    @Mapping(source = "userId", target = "userId", qualifiedByName = "mappUuidToByte")
    @Mapping(source = "userDto.userName", target = "userName")
    @Mapping(source = "userDto.email", target = "email")
    @Mapping(source = "now", target = "createdDate")
    @Mapping(source = "now", target = "updatedDate")
    NsUser mapUserDtoToNsUser(UserDto userDto, Date now, UUID userId);

    @Mapping(source = "user.userId", target = "userAuthDetailId")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "userDto.authType", target = "authType", qualifiedByName = "mapUserAuthType")
    @Mapping(source = "userDto.password", target = "password", qualifiedByName = "mapUserPassword")
    @Mapping(target = "accountNonLocked", constant = UserConstant.Y)
    @Mapping(target = "accountNonExpired", constant = UserConstant.Y)
    @Mapping(target = "credentialsNonExpired", constant = UserConstant.Y)
    @Mapping(source = "userDto", target = "enabled", qualifiedByName = "mapUserEnabled")
    @Mapping(source = "user.createdDate", target = "createdDate")
    @Mapping(source = "user.updatedDate", target = "updatedDate")
    NsUserAuthDetail mapUserDtoToNsUserAuthDetail(NsUser user, UserDto userDto);

//    check how to generate mappings with void return type with 2 arguments.
//    @Mapping(source = "user.userId", target = "userResponse.userId", qualifiedByName = "mapByteToUuid")
//    @Mapping(source = "user.userName", target = "userResponse.userName")
//    @Mapping(source = "user.email", target = "userResponse.email")
//    @Mapping(source = "user.createdDate", target = "userResponse.createdDate")
//    @Mapping(source = "user.updatedDate", target = "userResponse.updatedDate")
//    void mapUserToUserResponse(NsUser user, UserResponse userResponse);

}
