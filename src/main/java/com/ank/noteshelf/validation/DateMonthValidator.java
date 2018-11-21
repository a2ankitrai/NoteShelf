package com.ank.noteshelf.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ank.noteshelf.resource.Month;
import com.ank.noteshelf.validation.annotation.DateMonth;

public class DateMonthValidator implements ConstraintValidator<DateMonth, String> {

	public static final Logger logger = LoggerFactory.getLogger(DateMonthValidator.class);
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		String errorMessage = null;
		boolean isValid = true;
		
		if(value != null && value.length() != 6) { 
			return false;
		}
 
		try {
			
			Integer date = Integer.parseInt(value.split("-")[0]);
			String month = value.split("-")[1];
			
			if(date <=0 || date > 31) {
				errorMessage = "Enterd date is not valid. Please enter Date between 1 and 31.";
				isValid = false;
			}
			
			Month monthEnum = Month.fromString(month);
			if(monthEnum == null) {
				errorMessage += "\nEnterd month is not valid. Please enter in MMM format";
				isValid = false;
			}
			
		}
		catch(Exception e) {
			logger.debug("DateMonthValidator :: Exception occured: " + e.getMessage());
			return false;
		} 
		
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
		return isValid;
	}

}
