package com.titi.titi_auth.application.service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.common.constant.AuthConstants;
import com.titi.titi_auth.application.port.in.VerifyAuthCodeUseCase;
import com.titi.titi_auth.application.port.out.GetAuthCodePort;
import com.titi.titi_auth.application.port.out.RemoveAuthCodePort;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;
import com.titi.titi_auth.common.TiTiAuthException;
import com.titi.titi_auth.domain.AuthCode;
import com.titi.titi_common_lib.util.JwtUtils;

@Service
@RequiredArgsConstructor
public class VerifyAuthCodeService implements VerifyAuthCodeUseCase {

	private final GetAuthCodePort getAuthCodePort;
	private final RemoveAuthCodePort removeAuthCodePort;
	private final JwtUtils jwtUtils;

	@Override
	@Transactional
	public Result invoke(Command command) {
		final GetAuthCodePort.Result getAuthCodeResult = this.getAuthCodePort.invoke(GetAuthCodePort.Command.builder().authKey(command.authKey()).build());
		this.validateAuthCode(command.authCode(), getAuthCodeResult.authCode());
		this.removeAuthCodePort.invoke(RemoveAuthCodePort.Command.builder().authKey(command.authKey()).build());
		final String authToken = this.generateAuthToken(command.authKey());
		return Result.builder().authToken(authToken).build();
	}

	private void validateAuthCode(String authCode, Optional<AuthCode> cachedAuthCode) {
		if (cachedAuthCode.isEmpty()) {
			throw new TiTiAuthException(TiTiAuthBusinessCodes.INVALID_AUTH_CODE);
		}
		if (!cachedAuthCode.get().authCode().equals(authCode)) {
			throw new TiTiAuthException(TiTiAuthBusinessCodes.MISMATCHED_AUTH_CODE);
		}
	}

	private String generateAuthToken(String authKey) {
		final Instant now = Instant.now();
		return this.jwtUtils.generate(
			JwtUtils.Payload.builder()
				.registeredClaim(
					JwtUtils.Payload.RegisteredClaim.builder()
						.issuer(AuthConstants.JWT_ISSUER)
						.subject(authKey)
						.expirationTime(Date.from(now.plusSeconds(AUTH_TOKEN_EXPIRATION_TIME)))
						.issuedAt(Date.from(now))
						.jwtId(UUID.randomUUID().toString())
						.build()
				)
				.build()
		);
	}

}
