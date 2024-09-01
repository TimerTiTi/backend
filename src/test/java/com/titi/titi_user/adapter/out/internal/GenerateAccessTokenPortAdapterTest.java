package com.titi.titi_user.adapter.out.internal;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_auth.application.port.in.GenerateAccessTokenUseCase;
import com.titi.titi_user.application.port.out.internal.GenerateAccessTokenPort;

@ExtendWith(MockitoExtension.class)
class GenerateAccessTokenPortAdapterTest {

	@Mock
	private GenerateAccessTokenUseCase generateAccessTokenUseCase;

	@InjectMocks
	private GenerateAccessTokenPortAdapter generateAccessTokenPortAdapter;

	@Test
	void invoke() {
		// given
		final GenerateAccessTokenUseCase.Result mockResult = mock(GenerateAccessTokenUseCase.Result.class);
		given(mockResult.accessToken()).willReturn("accessToken");
		given(mockResult.refreshToken()).willReturn("refreshToken");
		given(generateAccessTokenUseCase.invoke(any(GenerateAccessTokenUseCase.Command.class))).willReturn(mockResult);

		// when
		final GenerateAccessTokenPort.Command command = GenerateAccessTokenPort.Command.builder()
			.memberId(String.valueOf(1L))
			.deviceId(UUID.randomUUID().toString())
			.build();
		final GenerateAccessTokenPort.Result result = generateAccessTokenPortAdapter.invoke(command);

		// then
		assertThat(result).isNotNull();
		assertThat(result.accessToken()).isEqualTo(mockResult.accessToken());
		assertThat(result.refreshToken()).isEqualTo(mockResult.refreshToken());
		verify(generateAccessTokenUseCase, times(1)).invoke(any(GenerateAccessTokenUseCase.Command.class));
	}

}