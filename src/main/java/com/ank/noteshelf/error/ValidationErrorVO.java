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

	//private List<FieldErrorVO> fieldErrors;
	private Map<String, List<String>> validationErrors;

	public void addFieldError(String field, String message) {

		FieldErrorVO fieldErrorVO = new FieldErrorVO(field, message);

		if (validationErrors == null) {
			validationErrors = new HashMap<>();
		}
		if (validationErrors.get(fieldErrorVO.getField()) == null) {
			List<String> errorList = new ArrayList<>();
			errorList.add(fieldErrorVO.getMessage());
			validationErrors.put(fieldErrorVO.getField(), errorList);
		} else {
			validationErrors.get(fieldErrorVO.getField()).add(fieldErrorVO.getMessage());
		}
	}

	/**
	public void addFieldError2(String field, String message) {

		FieldErrorVO fieldErrorVO = new FieldErrorVO(field, message);

		if (fieldErrors == null) {
			fieldErrors = new ArrayList<>();
		}
		fieldErrors.add(fieldErrorVO);
	}
	*/
}
