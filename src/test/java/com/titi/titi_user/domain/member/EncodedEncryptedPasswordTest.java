package com.titi.titi_user.domain.member;

import static org.assertj.core.api.Assertions.*;

import java.util.Base64;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.titi.exception.TiTiException;
import com.titi.titi_auth.domain.EncodedEncryptedPassword;
import com.titi.titi_crypto_lib.constant.AESCipherModes;
import com.titi.titi_crypto_lib.util.AESUtils;

class EncodedEncryptedPasswordTest {

	@Nested
	class GetRawPassword {

		private static final byte[] SECRET_KEY = "12345678901234567890123456789012".getBytes();

		@Test
		void successfulScenario() {
			// given
			final String rawPassword = "qlalfqjsgh1!";
			final byte[] encryptedPassword = AESUtils.encrypt(SECRET_KEY, rawPassword.getBytes(), AESCipherModes.GCM_NO_PADDING);
			final EncodedEncryptedPassword encodedEncryptedPassword = EncodedEncryptedPassword.builder()
				.value(Base64.getUrlEncoder().encodeToString(encryptedPassword))
				.build();

			// when
			final String result = encodedEncryptedPassword.getRawPassword(SECRET_KEY);

			// then
			assertThat(result).isEqualTo(rawPassword);
		}

		@Test
		void failToDecodeBase64urlScenario() {
			// given
			final String rawPassword = "qlalfqjsgh1!";
			final EncodedEncryptedPassword encodedEncryptedPassword = EncodedEncryptedPassword.builder()
				.value(rawPassword)
				.build();

			// when
			final ThrowableAssert.ThrowingCallable throwingCallable = () -> encodedEncryptedPassword.getRawPassword(SECRET_KEY);

			// then
			assertThatCode(throwingCallable).isInstanceOf(TiTiException.class);
		}

		@Test
		void failToDecryptAES256Scenario() {
			// given
			final String rawPassword = "qlalfqjsgh1!";
			final EncodedEncryptedPassword encodedEncryptedPassword = EncodedEncryptedPassword.builder()
				.value(Base64.getUrlEncoder().encodeToString(rawPassword.getBytes()))
				.build();

			// when
			final ThrowableAssert.ThrowingCallable throwingCallable = () -> encodedEncryptedPassword.getRawPassword(SECRET_KEY);

			// then
			assertThatCode(throwingCallable).isInstanceOf(TiTiException.class);
		}

		@Test
		void failToMisMatchedPatternScenario() {
			// given
			final String rawPassword = "qlalfqjsgh";
			final byte[] encryptedPassword = AESUtils.encrypt(SECRET_KEY, rawPassword.getBytes(), AESCipherModes.GCM_NO_PADDING);
			final EncodedEncryptedPassword encodedEncryptedPassword = EncodedEncryptedPassword.builder()
				.value(Base64.getUrlEncoder().encodeToString(encryptedPassword))
				.build();

			// when
			final ThrowableAssert.ThrowingCallable throwingCallable = () -> encodedEncryptedPassword.getRawPassword(SECRET_KEY);

			// then
			assertThatCode(throwingCallable).isInstanceOf(TiTiException.class);
		}

	}

}