package com.ank.noteshelf.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ank.noteshelf.model.NsUserProfile;
import com.ank.noteshelf.resource.ProfileInput;
import com.ank.noteshelf.vo.ProfileVO;

@Mapper
public interface ProfileObjectsMapper {

	ProfileObjectsMapper INSTANCE = Mappers.getMapper(ProfileObjectsMapper.class);
	
	@Mapping(source = "profile.userProfileId", target = "profileId")
	@Mapping(source = "profile.gender", target = "gender")
	@Mapping(source = "profile.work", target = "work")
	@Mapping(source = "profile.contactNumber", target = "contactNumber")
	@Mapping(source = "profile.birthDate", target = "birthDate")
	@Mapping(source = "profile.birthYear", target = "birthYear")
	@Mapping(source = "profile.language", target = "language")
	@Mapping(source = "profile.createdDate", target = "createdDate")
	@Mapping(source = "profile.updatedDate", target = "updatedDate")
	ProfileVO mapUserProfileToProfileVO(NsUserProfile profile);
 
	@Mapping(source = "profileInput.gender", target = "gender")
	@Mapping(source = "profileInput.work", target = "work")
	@Mapping(source = "profileInput.contactNumber", target = "contactNumber")
	@Mapping(source = "profileInput.birthDate", target = "birthDate")
	@Mapping(source = "profileInput.birthYear", target = "birthYear")
	@Mapping(source = "profileInput.language", target = "language") 
	@Mapping(source = "existingProfile.userId", target = "userId")
	@Mapping(source = "existingProfile.userProfileId", target = "userProfileId")
	@Mapping(source = "existingProfile.createdDate", target = "createdDate")
	NsUserProfile mapProfileInputAndUserIdToUserProfile(ProfileInput profileInput, NsUserProfile existingProfile);
	
}
