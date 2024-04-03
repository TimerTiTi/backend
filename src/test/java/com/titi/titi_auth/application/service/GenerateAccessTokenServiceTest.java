package com.titi.titi_auth.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.titi_auth.application.port.in.GenerateAccessTokenUseCase;
import com.titi.titi_auth.application.port.out.cache.PutRefreshTokenPort;

@ExtendWith(MockitoExtension.class)
class GenerateAccessTokenServiceTest {

	@Mock
	private JwtService jwtService;

	@Mock
	private PutRefreshTokenPort putRefreshTokenPort;

	@InjectMocks
	private GenerateAccessTokenService generateAccessTokenService;

	@Test
	void invoke() {
		// given
		given(jwtService.generateAccessToken(any(JwtService.Subject.class), any(Instant.class))).willReturn("accessToken");
		given(jwtService.generateRefreshToken(any(JwtService.Subject.class), any(Instant.class))).willReturn("refreshToken");

		// when
		final GenerateAccessTokenUseCase.Command command = GenerateAccessTokenUseCase.Command.builder()
			.memberId(String.valueOf(1L))
			.deviceId(UUID.randomUUID().toString())
			.build();
		final GenerateAccessTokenUseCase.Result result = generateAccessTokenService.invoke(command);

		// then
		assertThat(result).isNotNull();
		assertThat(result).usingRecursiveComparison().isNotNull();
		verify(jwtService, times(1)).generateAccessToken(any(JwtService.Subject.class), any(Instant.class));
		verify(jwtService, times(1)).generateRefreshToken(any(JwtService.Subject.class), any(Instant.class));
		verify(putRefreshTokenPort, times(1)).invoke(any(PutRefreshTokenPort.Command.class));
	}

}