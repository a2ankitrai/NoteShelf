package com.ank.noteshelf.controller;

import javax.servlet.http.HttpServletRequest;
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

import com.ank.noteshelf.resource.UserDO;
import com.ank.noteshelf.resource.UserSignUpDetail;
import com.ank.noteshelf.service.UserService;



/**
 * @RequestMapping : Maps a URL pattern and/or HTTP method to a method or controller type.
 * */
@RequestMapping("/user")
@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping("/registration")
    @ResponseBody
	public ResponseEntity<UserDO> registerUser(@RequestBody @Valid UserSignUpDetail userSignUpDetail) {
		logger.debug("UserController :: userRegistration :: start ");
		
		UserDO userDO = null;
		
		userDO = userService.registerUser(userSignUpDetail);
		
		logger.debug("UserController :: userRegistration :: end ");
		return new ResponseEntity<UserDO>(userDO, HttpStatus.OK);
	}
	
	@GetMapping("/login")
	public String getLogin() {
		return "Login Page!";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<String> loginUser(HttpServletRequest request) {
		

		ResponseEntity<String> responseEntity = new ResponseEntity<String>("Login Successful", HttpStatus.OK);
		
	 
		return responseEntity;
		
		/**
		 * Check for session token 
		 * 
		 * if present - check session validity - if valid return the same 
		 * 
		 * if not present - check the user name password for authentication
		 * 	generate a session token and return in response.
		 * 
		 * for all other request session token should be validated..
		 * 
		 * 
		 * */
	}
	
	@GetMapping("/logout")
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logoutUser(HttpSession session) {
		logger.info("logout: Start: ");
		logger.info("invalidating the session: ");
		session.invalidate();
		logger.info("logout: end: ");
	}
}
