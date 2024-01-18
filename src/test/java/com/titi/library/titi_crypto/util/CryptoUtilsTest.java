package com.titi.library.titi_crypto.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CryptoUtilsTest {

	private static final byte[] KEY_32BYTES = "12345678901234567890123456789012".getBytes();
	private static final byte[] INPUT_DATA = "inputData".getBytes();
	private final CryptoUtils cryptoUtils = new CryptoUtils(KEY_32BYTES);

	@Test
	void encryptTest() {
		assertThatCode(() -> cryptoUtils.encrypt(INPUT_DATA)).doesNotThrowAnyException();
	}

	@Test
	void decryptTest() {
		final byte[] encryptedData = cryptoUtils.encrypt(INPUT_DATA);
		assertThat(cryptoUtils.decrypt(encryptedData)).isEqualTo(INPUT_DATA);
	}
}
