package com.ank.noteshelf.service;

import com.ank.noteshelf.input.UserRegistrationInput;
import com.ank.noteshelf.response.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRegistrationInput userSignUpDetail);
    
    UserResponse getUserByUserId(byte[] userId);

}
