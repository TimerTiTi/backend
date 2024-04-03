package com.titi.titi_user.adapter.out.internal;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_auth.adapter.in.internal.GenerateAccessTokenGateway;
import com.titi.titi_user.application.port.out.internal.GenerateAccessTokenPort;

@ExtendWith(MockitoExtension.class)
class GenerateAccessTokenPortAdapterTest {

	@Mock
	private GenerateAccessTokenGateway generateAccessTokenGateway;

	@InjectMocks
	private GenerateAccessTokenPortAdapter generateAccessTokenPortAdapter;

	@Test
	void invoke() {
		// given
		final GenerateAccessTokenGateway.GenerateAccessTokenResponse response = GenerateAccessTokenGateway.GenerateAccessTokenResponse.builder()
			.accessToken("accessToken")
			.refreshToken("refreshToken")
			.build();
		given(generateAccessTokenGateway.invoke(any(GenerateAccessTokenGateway.GenerateAccessTokenRequest.class))).willReturn(response);

		// when
		final GenerateAccessTokenPort.Command command = GenerateAccessTokenPort.Command.builder()
			.memberId(String.valueOf(1L))
			.deviceId(UUID.randomUUID().toString())
			.build();
		final GenerateAccessTokenPort.Result result = generateAccessTokenPortAdapter.invoke(command);

		// then
		assertThat(result).isNotNull();
		assertThat(result).usingRecursiveComparison().isEqualTo(response);
		verify(generateAccessTokenGateway, times(1)).invoke(any(GenerateAccessTokenGateway.GenerateAccessTokenRequest.class));
	}

}