package com.ank.noteshelf.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ValidationErrorVO {

    // private List<nsFieldError> fieldErrors;
    private Map<String, List<String>> validationErrors;

    public void addFieldError(String field, String message) {

	NsFieldError nsFieldError = new NsFieldError(field, message);

	if (validationErrors == null) {
	    validationErrors = new HashMap<>();
	}
	if (validationErrors.get(nsFieldError.getField()) == null) {
	    List<String> errorList = new ArrayList<>();
	    errorList.add(nsFieldError.getMessage());
	    validationErrors.put(nsFieldError.getField(), errorList);
	} else {
	    validationErrors.get(nsFieldError.getField()).add(nsFieldError.getMessage());
	}
    }

    /**
     * public void addFieldError2(String field, String message) {
     * 
     * nsFieldError nsFieldError = new nsFieldError(field, message);
     * 
     * if (fieldErrors == null) { fieldErrors = new ArrayList<>(); }
     * fieldErrors.add(nsFieldError); }
     */
}
