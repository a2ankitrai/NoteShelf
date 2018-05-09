package com.ank.apps.noteshelf.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.ank.apps.noteshelf.validation.annotation.AlphaNumeric;

public class AlphaNumericValidator implements ConstraintValidator<AlphaNumeric, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (!StringUtils.isAlphanumeric(value)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Only Alpha Numeric characters are allowed.")
					.addConstraintViolation();
			return false;
		}
		return true;
	}

}
