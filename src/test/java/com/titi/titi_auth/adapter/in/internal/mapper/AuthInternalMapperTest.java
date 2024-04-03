package com.titi.titi_auth.adapter.in.internal.mapper;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.titi.titi_auth.adapter.in.internal.GenerateAccessTokenGateway;
import com.titi.titi_auth.application.port.in.GenerateAccessTokenUseCase;

class AuthInternalMapperTest {

	@Nested
	class ToCommand {

		@Test
		void whenRequestIsNotNullScenario() {
			// given
			final GenerateAccessTokenGateway.GenerateAccessTokenRequest request = GenerateAccessTokenGateway.GenerateAccessTokenRequest.builder()
				.memberId(String.valueOf(1L))
				.deviceId(UUID.randomUUID().toString())
				.build();

			// when
			final GenerateAccessTokenUseCase.Command command = AuthInternalMapper.INSTANCE.toCommand(request);

			// then
			assertThat(command).isNotNull();
			assertThat(command).usingRecursiveComparison().isEqualTo(request);
		}

		@Test
		void whenRequestIsNullScenario() {
			// given
			final GenerateAccessTokenGateway.GenerateAccessTokenRequest request = null;

			// when
			final GenerateAccessTokenUseCase.Command command = AuthInternalMapper.INSTANCE.toCommand(request);

			// then
			assertThat(command).isNull();
		}

	}

	@Nested
	class ToResponse {

		@Test
		void whenResultIsNotNullScenario() {
			// given
			final GenerateAccessTokenUseCase.Result result = GenerateAccessTokenUseCase.Result.builder()
				.accessToken("accessToken")
				.refreshToken("refreshToken")
				.build();

			// when
			final GenerateAccessTokenGateway.GenerateAccessTokenResponse response = AuthInternalMapper.INSTANCE.toResponse(result);

			// then
			assertThat(response).isNotNull();
			assertThat(response).usingRecursiveComparison().isEqualTo(result);
		}

		@Test
		void whenResultIsNullScenario() {
			// given
			final GenerateAccessTokenUseCase.Result result = null;

			// when
			final GenerateAccessTokenGateway.GenerateAccessTokenResponse response = AuthInternalMapper.INSTANCE.toResponse(result);

			// then
			assertThat(response).isNull();
		}

	}

}