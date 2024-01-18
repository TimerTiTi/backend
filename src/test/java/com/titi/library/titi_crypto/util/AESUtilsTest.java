package com.titi.library.titi_crypto.util;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.titi.library.titi_crypto.constant.AESCipherModes;
import com.titi.library.titi_crypto.exception.TiTiCryptoException;

class AESUtilsTest {

	private static final byte[] KEY_16BYTES = "1234567890123456".getBytes();
	private static final byte[] KEY_24BYTES = "123456789012345678901234".getBytes();
	private static final byte[] KEY_32BYTES = "12345678901234567890123456789012".getBytes();
	private static final byte[] WRONG_KEY_LENGTH_BYTES = "12345678".getBytes();
	private static final byte[] INPUT_DATA = "inputData".getBytes();
	private static final byte[] EMPTY_BYTE = new byte[0];

	private static Stream<Arguments> getAESCipherModes() {
		return Arrays.stream(AESCipherModes.values()).map(Arguments::of);
	}

	private static Stream<Arguments> getInvalidKeyOrData() {
		return Stream.of(
			Arguments.of(KEY_32BYTES, null),
			Arguments.of(KEY_32BYTES, EMPTY_BYTE),
			Arguments.of(null, INPUT_DATA),
			Arguments.of(EMPTY_BYTE, INPUT_DATA)
		);
	}

	@ParameterizedTest
	@MethodSource(value = "getAESCipherModes")
	void encryptByWrongKeyLengthThenThrowsTiTiCryptoException(AESCipherModes cipherMode) {
		assertThatCode(() -> AESUtils.encrypt(WRONG_KEY_LENGTH_BYTES, INPUT_DATA, cipherMode)).isInstanceOf(TiTiCryptoException.class);
	}

	@ParameterizedTest
	@MethodSource(value = "getInvalidKeyOrData")
	void encryptTest(byte[] key, byte[] data) {
		assertThatCode(() -> AESUtils.encrypt(key, data, AESCipherModes.CTR_NO_PADDING)).isInstanceOf(TiTiCryptoException.class);
	}

	@ParameterizedTest
	@MethodSource(value = "getAESCipherModes")
	void encryptTest(AESCipherModes cipherMode) {
		assertThatCode(() -> AESUtils.encrypt(KEY_16BYTES, INPUT_DATA, cipherMode)).doesNotThrowAnyException();
		assertThatCode(() -> AESUtils.encrypt(KEY_24BYTES, INPUT_DATA, cipherMode)).doesNotThrowAnyException();
		assertThatCode(() -> AESUtils.encrypt(KEY_32BYTES, INPUT_DATA, cipherMode)).doesNotThrowAnyException();
	}

	@ParameterizedTest
	@MethodSource(value = "getAESCipherModes")
	void decryptTest(AESCipherModes cipherMode) {
		assertThat(AESUtils.decrypt(KEY_16BYTES, AESUtils.encrypt(KEY_16BYTES, INPUT_DATA, cipherMode), cipherMode)).isEqualTo(INPUT_DATA);
		assertThat(AESUtils.decrypt(KEY_24BYTES, AESUtils.encrypt(KEY_24BYTES, INPUT_DATA, cipherMode), cipherMode)).isEqualTo(INPUT_DATA);
		assertThat(AESUtils.decrypt(KEY_32BYTES, AESUtils.encrypt(KEY_32BYTES, INPUT_DATA, cipherMode), cipherMode)).isEqualTo(INPUT_DATA);
	}

}
