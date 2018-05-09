package com.ank.apps.noteshelf.error;

import org.junit.Test;

import org.junit.Assert;
 

public class FieldErrorVOTest {

	@Test
	public void testLombok() {
		
		String field = "Test Field";
		String message = "Test Message";
		
		FieldErrorVO errorVO = new FieldErrorVO(field, message);
		
		FieldErrorVO errorVO2 = new FieldErrorVO();
		errorVO2.setField(field);
		errorVO2.setMessage(message);
		 
		Assert.assertEquals(errorVO.getField(), field);
		Assert.assertEquals(errorVO.getMessage(), message);
		
		Assert.assertEquals(errorVO2.getField(), field);
		Assert.assertEquals(errorVO2.getMessage(), message);
	}
}
