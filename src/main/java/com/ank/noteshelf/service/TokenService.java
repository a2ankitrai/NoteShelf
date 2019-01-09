package com.ank.noteshelf.service;

import com.ank.noteshelf.dto.TokenDto;
import com.ank.noteshelf.response.TokenVerificationResponse;

public interface TokenService {

    TokenDto createToken(String tokenType, byte[] userId);
    
    TokenVerificationResponse verifyToken(String tokenValue);
}
