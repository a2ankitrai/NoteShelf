package com.ank.noteshelf.resource;

import java.util.Arrays;

public enum Month {

	JAN("JAN"),
	FEB("FEB"),
	MAR("MAR"),
	APR("APR"),
	MAY("MAY"),
	JUN("JUN"),
	JUL("JUL"),
	AUG("AUG"),
	SEP("SEP"),
	OCT("OCT"),
	NOV("NOV"),
	DEC("DEC");
	
	private String value;

	private Month(String value) {
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
	
	public static Month fromString(String val) {
		return Arrays.stream(Month.values())
				.filter(at -> at.value.equalsIgnoreCase(val))
				.findFirst()
				.orElse(null);
	}
}
