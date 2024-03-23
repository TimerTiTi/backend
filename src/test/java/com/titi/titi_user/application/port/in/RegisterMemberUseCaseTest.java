package com.titi.titi_user.application.port.in;

import static org.assertj.core.api.Assertions.*;

import java.util.Base64;
import java.util.stream.Stream;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.titi.exception.TiTiException;
import com.titi.titi_crypto_lib.constant.AESCipherModes;
import com.titi.titi_crypto_lib.util.AESUtils;
import com.titi.titi_crypto_lib.util.HashingUtils;
import com.titi.titi_user.common.TiTiUserException;

class RegisterMemberUseCaseTest {

	@Nested
	class GetRawPassword {

		private static final byte[] WRAPPING_KEY = "803984986d8211443170c42a37d33d61".getBytes();

		@Test
		void successfulScenario() {
			// given
			final String rawPassword = "qlalfqjsgh1!";
			final byte[] encryptedPassword = AESUtils.encrypt(WRAPPING_KEY, rawPassword.getBytes(), AESCipherModes.GCM_NO_PADDING);
			final String encodedEncryptedPassword = Base64.getUrlEncoder().encodeToString(encryptedPassword);
			
			final RegisterMemberUseCase.Command command = RegisterMemberUseCase.Command.builder()
				.encodedEncryptedPassword(encodedEncryptedPassword)
				.build();

			// when
			final String unwrapPassword = command.getRawPassword(WRAPPING_KEY);

			// then
			assertThat(rawPassword).isEqualTo(unwrapPassword);
		}

		@Test
		void failToDecodeBase64urlScenario() {
			// given
			final String rawPassword = "qlalfqjsgh1!";

			final RegisterMemberUseCase.Command command = RegisterMemberUseCase.Command.builder()
				.encodedEncryptedPassword(rawPassword)
				.build();

			// when
			final ThrowableAssert.ThrowingCallable throwingCallable = () -> command.getRawPassword(WRAPPING_KEY);

			// then
			assertThatCode(throwingCallable).isInstanceOf(TiTiException.class);
		}

		@Test
		void failToDecryptAES256Scenario() {
			// given
			final String rawPassword = "qlalfqjsgh1!";
			final String encodedPassword = Base64.getUrlEncoder().encodeToString(rawPassword.getBytes());

			final RegisterMemberUseCase.Command command = RegisterMemberUseCase.Command.builder()
				.encodedEncryptedPassword(encodedPassword)
				.build();

			// when
			final ThrowableAssert.ThrowingCallable throwingCallable = () -> command.getRawPassword(WRAPPING_KEY);

			// then
			assertThatCode(throwingCallable).isInstanceOf(TiTiException.class);
		}

		@Test
		void failToMisMatchedPatternScenario() {
			// given
			final String rawPassword = "qlalfqjsgh";
			final byte[] encryptedPassword = AESUtils.encrypt(WRAPPING_KEY, rawPassword.getBytes(), AESCipherModes.GCM_NO_PADDING);
			final String encodedEncryptedPassword = Base64.getUrlEncoder().encodeToString(encryptedPassword);

			final RegisterMemberUseCase.Command command = RegisterMemberUseCase.Command.builder()
				.encodedEncryptedPassword(encodedEncryptedPassword)
				.build();

			// when
			final ThrowableAssert.ThrowingCallable throwingCallable = () -> command.getRawPassword(WRAPPING_KEY);

			// then
			assertThatCode(throwingCallable).isInstanceOf(TiTiException.class);
		}

	}

	@Nested
	class ValidateAuthKey {

		private static Stream<Arguments> failureScenario() {
			return Stream.of(
				Arguments.of("asfdsdaf", "test@gmail.com"),
				Arguments.of("ac_SU_E", "wrong@gmail.com")
			);
		}

		@Test
		void successfulScenario() {
			// given
			final String username = "test@gmail.com";
			final String authKey = HashingUtils.hashSha256("ac_SU_E", username);

			final RegisterMemberUseCase.Command command = RegisterMemberUseCase.Command.builder()
				.username(username)
				.build();

			// when
			final ThrowableAssert.ThrowingCallable throwingCallable = () -> command.validateAuthKey(authKey);

			// then
			assertThatCode(throwingCallable).doesNotThrowAnyException();
		}

		@ParameterizedTest
		@MethodSource
		void failureScenario(String prefix, String username) {
			// given
			final String authKey = HashingUtils.hashSha256(prefix, username);

			final RegisterMemberUseCase.Command command = RegisterMemberUseCase.Command.builder()
				.username("test@gmail.com")
				.build();

			// when
			final ThrowableAssert.ThrowingCallable throwingCallable = () -> command.validateAuthKey(authKey);

			// then
			assertThatCode(throwingCallable).isInstanceOf(TiTiUserException.class);
		}

	}

}