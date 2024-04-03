package com.titi.titi_auth.application.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.port.in.GenerateAccessTokenUseCase;
import com.titi.titi_auth.application.port.out.cache.PutRefreshTokenPort;

@Service
@RequiredArgsConstructor
class GenerateAccessTokenService implements GenerateAccessTokenUseCase {

	private final JwtService jwtService;
	private final PutRefreshTokenPort putRefreshTokenPort;

	@Override
	public Result invoke(Command command) {
		final JwtService.Subject subject = JwtService.Subject.builder()
			.memberId(command.memberId())
			.deviceId(command.deviceId())
			.build();
		Instant now = Instant.now();
		final String accessToken = this.jwtService.generateAccessToken(subject, now);
		final String refreshToken = this.jwtService.generateRefreshToken(subject, now);
		this.putRefreshTokenPort.invoke(
			PutRefreshTokenPort.Command.builder()
				.subject(subject.generateHashValue())
				.refreshToken(refreshToken)
				.build()
		);
		return Result.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

}
