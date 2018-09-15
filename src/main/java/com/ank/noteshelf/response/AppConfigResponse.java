package com.ank.noteshelf.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppConfigResponse {

    private String configurationName;
    private String configurationValue;
    private boolean lazyLoadDisabled;
    private Date createdDate;
    private Date updatedDate;
    
}
