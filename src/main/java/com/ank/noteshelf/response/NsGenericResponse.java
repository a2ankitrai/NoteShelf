package com.ank.noteshelf.response;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NsGenericResponse {

    private String message;
    private Date timeStamp;

    private int errorCode;
    private String errorMessage;
    private HttpStatus status;

    public NsGenericResponse(String message, Date timeStamp) {
	this.message = message;
	this.timeStamp = timeStamp;
    }
}
