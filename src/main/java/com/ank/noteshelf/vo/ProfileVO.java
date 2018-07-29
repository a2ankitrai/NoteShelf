package com.ank.noteshelf.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfileVO {

	private int profileId;
	private String gender;
	private String work;
	private String contactNumber;
	private String birthDate;
	private String birthYear;
	private String language;
	private Date createdDate;
	private Date updatedDate;
	
}
