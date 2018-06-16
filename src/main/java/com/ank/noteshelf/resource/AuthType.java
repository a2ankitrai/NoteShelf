package com.ank.noteshelf.resource;

import java.util.Arrays;

public enum AuthType {

	APP("APP"),
	GOOGLE("GOOGLE"),
	FACEBOOK("FACEBOOK"),
	GITHUB("GITHUB"),
	LINKEDIN("LINKEDIN");
	
	private String value;
	
	AuthType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return this.value;
	}
	
	public static AuthType fromString(String val) {
		return Arrays.stream(AuthType.values())
				.filter(at -> at.value.equalsIgnoreCase(val))
				.findFirst()
				.orElse(null);
	}
}
