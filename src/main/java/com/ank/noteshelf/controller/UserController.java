package com.ank.noteshelf.controller;

import static com.ank.noteshelf.resource.NsCommonConstant.USER_LOGIN_DETAIL;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ank.noteshelf.input.UserLoginDetail;
import com.ank.noteshelf.input.UserRegistrationInput;
import com.ank.noteshelf.response.NsGenericResponse;
import com.ank.noteshelf.response.UserResponse;
import com.ank.noteshelf.service.UserService;

/**
 * @RequestMapping : Maps a URL pattern and/or HTTP method to a method or
 *                 controller type.
 */

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/registration")
    @ResponseBody
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRegistrationInput userSignUpDetail) {

	UserResponse userResponse = null;
	userResponse = userService.registerUser(userSignUpDetail);
	return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
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
    public ResponseEntity<UserResponse> loginUser(HttpSession session) {
	UserLoginDetail userLoginDetail = (UserLoginDetail) SecurityContextHolder.getContext().getAuthentication()
		.getPrincipal();
	session.setAttribute(USER_LOGIN_DETAIL, userLoginDetail);
	
	UserResponse userResponse = userService.getUserByUserId(userLoginDetail.getUserId());

	ResponseEntity<UserResponse> responseEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);

	return responseEntity;
    }

    /**
     * Check on implementing the below...
     * 
     * Spring Security has built in support for a /logout end point which will do the
     * right thing for us (clear the session and invalidate the cookie). To
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
	NsGenericResponse response = new NsGenericResponse("Logout Successfull", new Date());
	ResponseEntity<NsGenericResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

	return responseEntity;
    }

    @GetMapping("/detail")
    public ResponseEntity<UserResponse> getUserDetail(HttpSession session) {
	UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute(USER_LOGIN_DETAIL);
	UserResponse userResponse = userService.getUserByUserId(userLoginDetail.getUserId());
	return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
    }

}
