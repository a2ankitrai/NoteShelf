package com.ank.noteshelf.mapstruct;

import java.util.Base64;

import org.mapstruct.Named;

public class AppConfigMapperFunctions {

    @Named("mapIsLazyLoadDisabledBooleanToByte")
    public Byte mapIsLazyLoadDisabledBooleanToByte(boolean lazyLoadDisabled) {
	Byte value = null;
	if (lazyLoadDisabled) {
	    value = (byte) 1;
	} else {
	    value = (byte) 0;
	}

	return value;
    }

    @Named("mapIsLazyLoadDisabledByteToBoolean")
    public boolean mapIsLazyLoadDisabledByteToBoolean(Byte isLazyLoadDisabled) {
	return isLazyLoadDisabled == 1 ? true : false;
    }

    @Named("mapConfigValueEncoded")
    public String mapConfigValueAfterEncoding(String configValue) {
	return Base64.getEncoder().encodeToString(configValue.getBytes());
    }

    @Named("mapConfigValueDecoded")
    public String mapConfigValueDecoded(String configValue) {
	String s = new String(Base64.getDecoder().decode(configValue));
	return s;
    }

}
