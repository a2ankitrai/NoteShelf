package com.ank.apps.noteshelf.resource;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ank.apps.noteshelf.validation.annotation.AlphaNumeric;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSignUpDetail {

	@NotNull
	@AlphaNumeric
    @NotEmpty(message="User Name cannot be empty")
	@JsonProperty("user_name")
	private String userName;
	
	@NotNull
    @NotEmpty
    @AlphaNumeric
	@JsonProperty("first_name")
	private String firstName;
	
	@JsonProperty("last_name")
	@AlphaNumeric
	private String lastName;
	
	@NotNull
    @NotEmpty
	@JsonProperty("email_address")
	private String emailAddress;
	
	@NotNull
    @NotEmpty
	@JsonProperty("password")
	private String password;
	
	@NotNull
    @NotEmpty
	@JsonProperty("confirm_password")
	private String confirmPassword;

	@JsonProperty("user_name")
	public String getUserName() {
		return userName;
	}

	@JsonProperty("user_name")
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("first_name")
	public String getFirstName() {
		return firstName;
	}

	@JsonProperty("first_name")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonProperty("last_name")
	public String getLastName() {
		return lastName;
	}

	@JsonProperty("last_name")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty("email_address")
	public String getEmailAddress() {
		return emailAddress;
	}

	@JsonProperty("email_address")
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonProperty("confirm_password")
	public String getConfirmPassword() {
		return confirmPassword;
	}

	@JsonProperty("confirm_password")
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
