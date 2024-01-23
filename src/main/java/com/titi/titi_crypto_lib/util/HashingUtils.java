package com.titi.titi_crypto_lib.util;

import static com.titi.titi_common_lib.constant.Constants.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.titi.titi_crypto_lib.constant.HashAlgorithms;
import com.titi.titi_crypto_lib.exception.TiTiCryptoException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HashingUtils {

	/**
	 * Generates a SHA-256 hash of concatenated input strings using Base64 encoding.
	 *
	 * @param inputs The variable number of input strings to be concatenated and hashed.
	 * @return The SHA-256 hash as a Base64-encoded string.
	 */
	public static String hashSha256(String... inputs) {
		return hashCore(inputs, HashAlgorithms.SHA_256);
	}

	private static String hashCore(String[] inputs, HashAlgorithms hashAlgorithms) {
		try {
			final MessageDigest digest = MessageDigest.getInstance(hashAlgorithms.getAlgorithm());
			final byte[] hash = digest.digest(String.join(UNDERSCORE, inputs).getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new TiTiCryptoException("Invalid hash algorithm.", e);
		}
	}

}
