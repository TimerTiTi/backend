package com.titi.titi_crypto_lib.util;

import com.titi.titi_crypto_lib.constant.AESCipherModes;

public final class CryptoUtils {

	private final byte[] secretKey;

	public CryptoUtils(byte[] secretKey) {
		this.secretKey = secretKey;
	}

	public byte[] encrypt(byte[] data) {
		return AESUtils.encrypt(secretKey, data, AESCipherModes.GCM_NO_PADDING);
	}

	public byte[] decrypt(byte[] data) {
		return AESUtils.decrypt(secretKey, data, AESCipherModes.GCM_NO_PADDING);
	}

}
