package com.ank.noteshelf.response;

import org.springframework.http.HttpStatus;

import com.ank.noteshelf.dto.TokenDto;

import lombok.Data;

@Data
public class TokenVerificationResponse {

    private TokenDto tokenDto;

    private int errorCode;
    private String errorMessage;
    private HttpStatus status;
}
