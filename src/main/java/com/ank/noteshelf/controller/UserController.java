package com.ank.noteshelf.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ank.ankoauth2client.resource.AuthType;
import com.ank.ankoauth2client.resource.UserDto;
import com.ank.ankoauth2client.service.AnkOauth2Service;
import com.ank.noteshelf.dto.TokenDto;
import com.ank.noteshelf.event.UserEventPublisher;
import com.ank.noteshelf.exception.NsRuntimeException;
import com.ank.noteshelf.input.UserRegistrationInput;
import com.ank.noteshelf.response.NsGenericResponse;
import com.ank.noteshelf.response.TokenVerificationResponse;
import com.ank.noteshelf.service.TokenService;
import com.ank.noteshelf.service.UserService;
import com.ank.noteshelf.util.UserDetailUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @RequestMapping : Maps a URL pattern and/or HTTP method to a method or
 *                 controller type.
 */

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    AnkOauth2Service ankOauth2Service;

    @Autowired
    UserEventPublisher userEventPublisher;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/registration")
    @ResponseBody
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserRegistrationInput userSignUpDetail) {

	UserDto userDto = null;
	userDto = userService.registerAppUser(userSignUpDetail);

	return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    @GetMapping("/login")
    public String getLogin() {
	return "Login Page!";
    }

    /**
     * Login implementation with jwt using bearer token.
     * 
     * https://www.callicoder.com/spring-boot-spring-security-jwt-mysql-react-app-part-2/
     */

    // support login by username as well as email..
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<UserDto> loginUser(HttpSession session) {
	UserDetailUtil.setUserDetailsPrincipalIntoSession(session);
	UserDto userDto = UserDetailUtil.getUserDtoFromSession(session);
	return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    /**
     * Check on implementing the below...
     * 
     * Spring Security has built in support for a /logout end point which will do
     * the right thing for us (clear the session and invalidate the cookie). To
     * configure the end point we simply extend the existing configure() method in
     * our WebSecurityConfigurer: SocialApplication.java
     * 
     * @Override protected void configure(HttpSecurity http) throws Exception {
     *           http.antMatcher("/**") ... // existing code here
     *           .and().logout().logoutSuccessUrl("/").permitAll(); }
     */

    @PostMapping("/logout")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<NsGenericResponse> logoutUser(HttpSession session) {
	session.invalidate();
	NsGenericResponse response = new NsGenericResponse("Logout Successful", new Date());
	ResponseEntity<NsGenericResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

	return responseEntity;
    }

    @GetMapping("/detail")
    public ResponseEntity<UserDto> getUserDetail(HttpSession session) {
//	UserDto userResponse = userService.getUserByUserId(UserDetailUtil.getUserIdFromSession(session));
	UserDto userDto = UserDetailUtil.getUserDtoFromSession(session);
	return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<UserDto> getUserDto(HttpServletResponse response) {
	UserDto userDto = ankOauth2Service.getCurrentUser(response);
	return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    @PostMapping("/verify-registration-email")
    public ResponseEntity<TokenVerificationResponse> verifyRegistrationEmail(@RequestBody String tokenValue) {
	TokenVerificationResponse tokenVerificationResponse = tokenService.verifyToken(tokenValue);

	return new ResponseEntity<TokenVerificationResponse>(tokenVerificationResponse,
		tokenVerificationResponse.getStatus());
    }

    @PostMapping("/activate-user-account")
    public ResponseEntity<NsGenericResponse> activateUserAccount(@RequestBody TokenDto tokenDto) {

	ResponseEntity<NsGenericResponse> response = null;
	NsGenericResponse nsGenericResponse = userService.activateUserAccount(tokenDto.getUserName());

	response = new ResponseEntity<>(nsGenericResponse, nsGenericResponse.getStatus());
	return response;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<NsGenericResponse> forgotPasswordHandler(@RequestBody String userEmail) {
	UserDto userDto = userService.getUserByEmail(userEmail);
	NsGenericResponse response = new NsGenericResponse();
	if (userDto == null) {
	    response.setErrorCode(1);
	    response.setErrorMessage("Email address does not exist in the system");
	    response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
	} else if (userDto.getAuthType() != AuthType.APP) {
	    response.setErrorCode(2);
	    response.setErrorMessage("Email Address was registered through " + userDto.getAuthType().getValue()
		    + ". Password cannot be reset.");
	    response.setStatus(HttpStatus.OK);
	} else {
	    response.setErrorCode(0);
	    userEventPublisher.publishPasswordResetEmailEvent(userDto);
	    response.setMessage("Password reset link successfully sent to the registered email Address.");
	    response.setStatus(HttpStatus.OK);
	}
	return new ResponseEntity<NsGenericResponse>(response, response.getStatus());
    }

    @PostMapping("/verify-password-reset-token")
    public ResponseEntity<TokenVerificationResponse> verifyPasswordResetToken(@RequestBody String tokenValue) {
	TokenVerificationResponse tokenVerificationResponse = tokenService.verifyToken(tokenValue);
	return new ResponseEntity<TokenVerificationResponse>(tokenVerificationResponse,
		tokenVerificationResponse.getStatus());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<UserDto> resetPassword(@RequestBody String emailPassword) {
	ResponseEntity<UserDto> responseEntity = null;

	String email = null;
	String password = null;

	ObjectMapper objectMapper = new ObjectMapper();

	JsonNode jsonNode;
	try {
	    jsonNode = objectMapper.readTree(emailPassword);
	    email = jsonNode.get("email").asText();
	    password = jsonNode.get("password").asText();

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	    return responseEntity;
	}

	UserDto userDto = userService.resetPassword(email, password);
	if (userDto != null) {
	    responseEntity = new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	} else {
	    responseEntity = new ResponseEntity<UserDto>(userDto, HttpStatus.NOT_FOUND);
	}

	return responseEntity;
    }

}
