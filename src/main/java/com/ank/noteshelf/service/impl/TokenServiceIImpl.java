package com.ank.noteshelf.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ank.noteshelf.dto.TokenDto;
import com.ank.noteshelf.model.NsTokenStore;
import com.ank.noteshelf.model.NsUser;
import com.ank.noteshelf.repository.TokenStoreRepository;
import com.ank.noteshelf.repository.UserRepository;
import com.ank.noteshelf.response.TokenVerificationResponse;
import com.ank.noteshelf.service.TokenService;
import com.ank.noteshelf.util.UuidUtils;

@Service
public class TokenServiceIImpl implements TokenService {

    @Autowired
    TokenStoreRepository tokenStoreRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TokenDto createToken(String tokenType, byte[] userId) {

	NsTokenStore tokenStore = new NsTokenStore();
	byte[] tokenStoreId = UuidUtils.generateUuidBytes();

	tokenStore.setTokenStoreId(tokenStoreId);
	tokenStore.setTokenType(tokenType);
	tokenStore.setTokenValue(UuidUtils.generateRandomStringWithoutDashes());
	tokenStore.setUserId(userId);
	tokenStore.setCreateDate(new Date());

	tokenStore.setIsExpired(false);

	tokenStoreRepository.save(tokenStore);

	TokenDto tokenDto = mapTokenStoreToTokenDto(tokenStore);
	return tokenDto;
    }

    @Override
    public TokenVerificationResponse verifyToken(String tokenValue) {

	Optional<NsTokenStore> tokenStoreOptional = Optional
		.ofNullable(tokenStoreRepository.findByTokenValue(tokenValue));
	TokenDto tokenDto = tokenStoreOptional.map(t -> mapTokenStoreToTokenDto(t)).orElse(null);

	TokenVerificationResponse tokenVerificationResponse = new TokenVerificationResponse();

	if (tokenDto == null) {
	    tokenVerificationResponse.setErrorCode(1);
	    tokenVerificationResponse.setErrorMessage("Token does not exist. Invalid tokenValue input");
	    tokenVerificationResponse.setStatus(HttpStatus.BAD_REQUEST);
	} else if (tokenDto.isExpired()) {
	    tokenVerificationResponse.setErrorCode(2);
	    tokenVerificationResponse.setErrorMessage("Token has already expired. Please generate a new reset token through Forgot Password link.");
	    tokenVerificationResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
	} else if (tokenDto.getUserEmail() == null) {
	    tokenVerificationResponse.setErrorCode(3);
	    tokenVerificationResponse.setErrorMessage("The account associated with this token no longer exist.");
	    tokenVerificationResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
	} else {
	    tokenVerificationResponse.setErrorCode(0);
	    tokenVerificationResponse.setStatus(HttpStatus.OK);
	    tokenVerificationResponse.setTokenDto(tokenDto);

//	    check if token can be expired after password reset Ï
//	    NsTokenStore tokenStore = tokenStoreOptional.get();
//	    tokenStore.setIsExpired(mapByteToBoolean(TRUE_BYTE));
//	    tokenStore.setExpiredDate(new Date());
//	    tokenStoreRepository.save(tokenStore);

	}

	return tokenVerificationResponse;
    }

    private boolean mapByteToBoolean(byte b) {
	if (b == 0) {
	    return true;
	} else
	    return false;
    }

    private TokenDto mapTokenStoreToTokenDto(NsTokenStore tokenStore) {
	TokenDto tokenDto = new TokenDto();

	tokenDto.setTokenType(tokenStore.getTokenType());
	tokenDto.setTokenValue(tokenStore.getTokenValue());
	tokenDto.setCreatedDate(tokenStore.getCreateDate());
	tokenDto.setExpired(tokenStore.getIsExpired());

	byte[] userId = tokenStore.getUserId();

	Optional<NsUser> userOptional = userRepository.findByUserId(userId);
	tokenDto.setUserName(userOptional.map(u -> u.getUserName()).orElse(null));
	tokenDto.setUserEmail(userOptional.map(u -> u.getEmail()).orElse(null));

	return tokenDto;
    }
}
