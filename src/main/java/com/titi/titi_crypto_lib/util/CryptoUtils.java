package com.titi.titi_crypto_lib.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.titi.titi_crypto_lib.constant.AESCipherModes;

public final class CryptoUtils {

	private final byte[] secretKey;

	public CryptoUtils(byte[] secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * Encrypts the provided byte array using the AES (Advanced Encryption Standard) algorithm
	 * with GCM (Galois/Counter Mode) cipher mode and no padding.
	 *
	 * @param data The byte array to be encrypted.
	 * @return The encrypted byte array.
	 */
	public byte[] encrypt(byte[] data) {
		return AESUtils.encrypt(secretKey, data, AESCipherModes.GCM_NO_PADDING);
	}

	/**
	 * Decrypts the provided byte array using the AES (Advanced Encryption Standard) algorithm
	 * with GCM (Galois/Counter Mode) cipher mode and no padding.
	 *
	 * @param data The byte array to be decrypted.
	 * @return The decrypted byte array.
	 */
	public byte[] decrypt(byte[] data) {
		return AESUtils.decrypt(secretKey, data, AESCipherModes.GCM_NO_PADDING);
	}

	/**
	 * Encrypts the provided string using the AES (Advanced Encryption Standard) algorithm
	 * with GCM (Galois/Counter Mode) cipher mode and no padding, and returns the result
	 * as a Base64-encoded string.
	 *
	 * @param data The string to be encrypted.
	 * @return The Base64-encoded encrypted string.
	 */
	public String encryptToString(String data) {
		return Base64.getEncoder().encodeToString(AESUtils.encrypt(secretKey, data.getBytes(StandardCharsets.UTF_8), AESCipherModes.GCM_NO_PADDING));
	}

}
