package com.ank.noteshelf.resource;

import java.util.Arrays;

public enum Gender {

    MALE("M"), FEMALE("F"), OTHERS("O");

    private String value;

    Gender(String value) {
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

    public static Gender fromString(String val) {
	return Arrays.stream(Gender.values()).filter(g -> g.value.equalsIgnoreCase(val)).findFirst().orElse(null);
    }
}
