package com.ank.apps.noteshelf.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.ank.apps.noteshelf.resource.UserDO;
import com.ank.apps.noteshelf.resource.UserSignUpDetail;
import com.ank.apps.noteshelf.service.UserService;


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
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity loginUser() {
		return null;
	}
	
	@GetMapping("/logout")
	@ResponseBody
	public ResponseEntity logoutUser() {
		return null;
	}
}
