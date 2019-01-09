package com.ank.noteshelf.input;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationInput {

    @NotNull
    // @AlphaNumeric
    @NotEmpty(message = "User Name cannot be empty")
    @JsonProperty("user_name")
    private String userName;

    @NotNull
    @NotEmpty
    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("password")
    private String password;

    @JsonProperty("confirm_password")
    private String confirmPassword;

    @JsonProperty("auth_type")
    private String authType;

}
