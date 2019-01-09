package com.ank.noteshelf.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtils {

    public static UUID asUuid(byte[] bytes) {
	ByteBuffer bb = ByteBuffer.wrap(bytes);
	long firstLong = bb.getLong();
	long secondLong = bb.getLong();
	return new UUID(firstLong, secondLong);
    }

    public static byte[] asBytes(UUID uuid) {
	ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
	bb.putLong(uuid.getMostSignificantBits());
	bb.putLong(uuid.getLeastSignificantBits());
	return bb.array();
    }

    public static byte[] asBytesFromString(String uuid) {
	return asBytes(generatefromString(uuid));
    }

    public static byte[] generateUuidBytes() {
	return asBytes(generateRandomUuid());
    }

    public static UUID generateRandomUuid() {
	return UUID.randomUUID();
    }

    public static UUID generatefromString(String uuid) {
	return UUID.fromString(uuid);
    }

    public static String generateRandomStringWithoutDashes() {
	return generateRandomUuid().toString().replace("-", "");
    }

}
