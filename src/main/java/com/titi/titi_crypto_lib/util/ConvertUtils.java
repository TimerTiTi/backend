package com.titi.titi_crypto_lib.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConvertUtils {

	private static final String HEX_FORMAT = "%02X";

	static String byteArrayToHexString(byte[] byteArray) {
		final StringBuilder hexString = new StringBuilder();
		for (byte b : byteArray) {
			hexString.append(String.format(HEX_FORMAT, b));
		}
		return hexString.toString();
	}

}
