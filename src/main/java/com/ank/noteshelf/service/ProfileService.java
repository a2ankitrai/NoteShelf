package com.ank.noteshelf.service;

import org.springframework.web.multipart.MultipartFile;

import com.ank.noteshelf.input.ProfileInput;
import com.ank.noteshelf.response.NsGenericResponse;
import com.ank.noteshelf.response.PictureResponse;
import com.ank.noteshelf.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse getProfileByUserId(byte[] userId);

    ProfileResponse updateProfile(ProfileInput profileInput, byte[] userId);

    Boolean deleteProfileByUserId(byte[] userId);

    PictureResponse updateProfilePicture(MultipartFile picture, byte[] userId);

    NsGenericResponse deleteProfilePicture(byte[] userId);

    PictureResponse getProfilePictureByUserId(byte[] userId);
}
