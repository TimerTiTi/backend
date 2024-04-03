package com.titi.titi_auth.application.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.common.constant.AuthConstants;
import com.titi.titi_auth.application.port.in.ReissueAccessTokenUseCase;
import com.titi.titi_auth.application.port.out.cache.GetRefreshTokenPort;
import com.titi.titi_auth.application.port.out.cache.PutRefreshTokenPort;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;
import com.titi.titi_auth.common.TiTiAuthException;

@Service
@RequiredArgsConstructor
class ReissueAccessTokenService implements ReissueAccessTokenUseCase {

	private final GetRefreshTokenPort getRefreshTokenPort;
	private final PutRefreshTokenPort putRefreshTokenPort;
	private final JwtService jwtService;

	@Override
	public Result invoke(Command command) {
		final JwtService.Subject subject = this.jwtService.extractSubject(command.refreshToken(), AuthConstants.REFRESH_TOKEN);
		final String hashedSubject = subject.generateHashValue();
		this.validateRefreshToken(command.refreshToken(), hashedSubject);
		Instant now = Instant.now();
		final String accessToken = this.jwtService.generateAccessToken(subject, now);
		final String refreshToken = this.jwtService.generateRefreshToken(subject, now);
		this.putRefreshTokenPort.invoke(
			PutRefreshTokenPort.Command.builder()
				.subject(hashedSubject)
				.refreshToken(refreshToken)
				.build()
		);
		return Result.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	private void validateRefreshToken(String refreshToken, String subject) {
		final GetRefreshTokenPort.Result invoke = this.getRefreshTokenPort.invoke(GetRefreshTokenPort.Command.builder().subject(subject).build());
		final Optional<String> cachedRefreshToken = invoke.refreshToken();
		if (cachedRefreshToken.isEmpty() || !refreshToken.equals(cachedRefreshToken.get())) {
			throw new TiTiAuthException(TiTiAuthBusinessCodes.REISSUE_ACCESS_TOKEN_FAILURE_INVALID_REFRESH_TOKEN);
		}
	}

}
