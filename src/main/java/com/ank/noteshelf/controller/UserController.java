package com.ank.noteshelf.controller;

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
import com.ank.noteshelf.input.UserSignUpDetail;
import com.ank.noteshelf.response.NsGenericResponse;
import com.ank.noteshelf.response.UserResponse;
import com.ank.noteshelf.service.UserService;

/**
 * @RequestMapping : Maps a URL pattern and/or HTTP method to a method or controller type.
 */

@RequestMapping("/user")
@RestController
public class UserController {

  @Autowired
  UserService userService;

  public static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @PostMapping("/registration")
  @ResponseBody
  public ResponseEntity<UserResponse> registerUser(
      @RequestBody @Valid UserSignUpDetail userSignUpDetail) {

    UserResponse userResponse = null;
    userResponse = userService.registerUser(userSignUpDetail);
    return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
  }

  @GetMapping("/login")
  public String getLogin() {
    return "Login Page!";
  }

  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity<NsGenericResponse> loginUser(HttpSession session) {
    UserLoginDetail userLoginDetail =
        (UserLoginDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    session.setAttribute("userLoginDetail", userLoginDetail);

    NsGenericResponse response = new NsGenericResponse(
        "Login Successful! Welcome " + userLoginDetail.getUsername(), new Date());

    ResponseEntity<NsGenericResponse> responseEntity =
        new ResponseEntity<>(response, HttpStatus.OK);

    return responseEntity;
  }

  @PostMapping("/logout")
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<NsGenericResponse> logoutUser(HttpSession session) {
    session.invalidate();
    NsGenericResponse response = new NsGenericResponse("Logout Successfull", new Date());
    ResponseEntity<NsGenericResponse> responseEntity =
        new ResponseEntity<>(response, HttpStatus.OK);

    return responseEntity;
  }
}
