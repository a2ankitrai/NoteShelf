package com.ank.noteshelf.controller;

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

import com.ank.noteshelf.resource.UserLoginDetail;
import com.ank.noteshelf.resource.UserSignUpDetail;
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
	public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserSignUpDetail userSignUpDetail) {
		logger.debug("UserController :: userRegistration :: start ");

		UserResponse userResponse = null;

		userResponse = userService.registerUser(userSignUpDetail);

		logger.debug("UserController :: userRegistration :: end ");
		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
	}

	@GetMapping("/login")
	public String getLogin() {
		return "Login Page!";
	}

	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<String> loginUser(HttpSession session) {
		
		UserLoginDetail userLoginDetail = (UserLoginDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		session.setAttribute("userLoginDetail", userLoginDetail);
		   
		ResponseEntity<String> responseEntity = new ResponseEntity<String>("Login Successful! Welcome "+ userLoginDetail.getUsername(), HttpStatus.OK);
	 
		return responseEntity;
	}

	@GetMapping("/logout")
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logoutUser(HttpSession session) {
		logger.debug("UserController:: logoutUser :: Start");
		logger.info("invalidating the session: ");
		session.invalidate();
		logger.debug("UserController:: logoutUser :: End"); 
	}
}
