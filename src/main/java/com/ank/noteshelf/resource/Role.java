package com.ank.noteshelf.resource;

import java.util.Arrays;

public enum Role {

	USER("USER");
	
	String value;
	
	Role(String value) {
		this.value = value;
	}
	
	public String toString() {
		return this.value;
	}
	
	public static Role fromString(String val) {
		return Arrays.stream(Role.values())
				.filter(r -> r.value.equalsIgnoreCase(val))
				.findFirst()
				.orElse(null);
	}
}
