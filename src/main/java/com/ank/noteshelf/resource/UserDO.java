package com.ank.noteshelf.resource;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDO {

	private int userId;
	
	private String firstName;
	
	private String lastName;
	
	private String username;

	private AuthType authType; 
	
	private Role role;
	
	private Date createdDate;
	 
	private Date updatedDate;

	 
	
	
}
