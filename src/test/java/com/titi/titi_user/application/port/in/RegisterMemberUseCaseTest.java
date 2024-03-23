package com.titi.titi_user.application.port.in;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.titi.titi_crypto_lib.util.HashingUtils;
import com.titi.titi_user.common.TiTiUserException;

class RegisterMemberUseCaseTest {

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