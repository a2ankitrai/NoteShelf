package com.ank.noteshelf.service;

import com.ank.ankoauth2client.resource.UserDto;
import com.ank.noteshelf.input.UserRegistrationInput;

public interface UserService {

    UserDto registerAppUser(UserRegistrationInput userSignUpDetail);

    UserDto getUserByUserId(byte[] userId);

    UserDto registerUser(UserDto userDto);

    UserDto getUserByEmail(String email);

    UserDto resetPassword(String userEmail, String password) ;
}
