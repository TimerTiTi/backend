package com.titi.titi_auth.adapter.in.internal;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_auth.application.port.in.GenerateAccessTokenUseCase;

@ExtendWith(MockitoExtension.class)
class GenerateAccessTokenGatewayTest {

	@Mock
	private GenerateAccessTokenUseCase generateAccessTokenUseCase;

	@InjectMocks
	private GenerateAccessTokenGateway generateAccessTokenGateway;

	@Test
	void invoke() {
		// given
		final GenerateAccessTokenUseCase.Result result = GenerateAccessTokenUseCase.Result.builder()
			.accessToken("accessToken")
			.refreshToken("refreshToken")
			.build();
		given(generateAccessTokenUseCase.invoke(any(GenerateAccessTokenUseCase.Command.class))).willReturn(result);

		// when
		final GenerateAccessTokenGateway.GenerateAccessTokenRequest request = GenerateAccessTokenGateway.GenerateAccessTokenRequest.builder()
			.memberId(String.valueOf(1L))
			.deviceId(UUID.randomUUID().toString())
			.build();
		final GenerateAccessTokenGateway.GenerateAccessTokenResponse response = generateAccessTokenGateway.invoke(request);

		// then
		assertThat(response).isNotNull();
		assertThat(response).usingRecursiveComparison().isEqualTo(result);
		verify(generateAccessTokenUseCase, times(1)).invoke(any(GenerateAccessTokenUseCase.Command.class));
	}

}