package com.ank.noteshelf.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NsGenericResponse {

    private String message;
    private Date timeStamp;

}
