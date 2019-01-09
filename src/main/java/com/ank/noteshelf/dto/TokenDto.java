package com.ank.noteshelf.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String tokenType;
    private String tokenValue;
    private boolean isExpired;
    private Date createdDate;
    private Date expiredDate;
    
    private String userName;
    private String userEmail;

}
