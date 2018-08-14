package com.ank.noteshelf.mapstruct;

import java.util.UUID;

import org.mapstruct.Named;

import com.ank.noteshelf.util.UuidUtils;

public class NsCommonMapperFunctions {

	@Named("mappUuidToByte")
	public byte[] mapUuidToByte(UUID uuid) {
		return UuidUtils.asBytes(uuid);
	}
	
	@Named("mapByteToUuid")
	public UUID mapByteToUuid(byte[] bytes) {
		return UuidUtils.asUuid(bytes);
	}
}
