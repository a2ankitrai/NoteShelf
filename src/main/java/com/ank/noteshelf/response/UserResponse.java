package com.ank.noteshelf.response;

import java.util.Date;
import java.util.UUID;

import com.ank.noteshelf.resource.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private UUID userId;
    
    // firstName and LastName to be removed...
    private String firstName;
    private String lastName;

    private String userName;

    private String email;

    private String authType;

    private Role role;

    private UUID userProfileId;

    private Date createdDate;

    private Date updatedDate;

}
