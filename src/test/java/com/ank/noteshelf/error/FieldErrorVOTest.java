package com.ank.noteshelf.error;

import org.junit.Assert;
import org.junit.Test;

import com.ank.noteshelf.error.FieldErrorVO;
 

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
