package com.ank.noteshelf.response;

import java.util.Date;

import com.ank.noteshelf.resource.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	private int userId;
	 
	private String firstName;
	
	private String lastName;
	
	private String userName;

	private String authType; 
	
	private Role role;
	
	private int userProfileId;
	
	private Date createdDate;
	 
	private Date updatedDate;

	 
	
	
}
