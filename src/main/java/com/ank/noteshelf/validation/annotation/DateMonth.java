package com.ank.noteshelf.validation.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.ank.noteshelf.validation.DateMonthValidator;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER })
@Constraint(validatedBy = {DateMonthValidator.class})
public @interface DateMonth {
	
	String message() default "Entered date is not in valid format. Please enter a valid date";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
}
