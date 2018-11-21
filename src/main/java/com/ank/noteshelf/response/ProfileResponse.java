package com.ank.noteshelf.response;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfileResponse {

    @JsonProperty("profile_id")
    private UUID profileId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String gender;

    private String work;

    @JsonProperty("contact_number")
    private String contactNumber;

    @JsonProperty("birth_date")
    private String birthDate;

    @JsonProperty("birth_year")
    private String birthYear;

    private String language;

    @JsonProperty("created_date")
    private Date createdDate;

    @JsonProperty("updated_date")
    private Date updatedDate;

}
