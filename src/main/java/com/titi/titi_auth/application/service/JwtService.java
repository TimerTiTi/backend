package com.titi.titi_auth.application.service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.common.constant.AuthConstants;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;
import com.titi.titi_auth.common.TiTiAuthException;
import com.titi.titi_common_lib.util.JacksonHelper;
import com.titi.titi_common_lib.util.JwtUtils;
import com.titi.titi_crypto_lib.util.HashingUtils;

@Component
@RequiredArgsConstructor
class JwtService {

	private final JwtUtils jwtUtils;

	private String generateJWT(Subject subject, Date now, Date expirationTime, String type) {
		return this.jwtUtils.generate(
			JwtUtils.Payload.builder()
				.registeredClaim(
					JwtUtils.Payload.RegisteredClaim.builder()
						.issuer(AuthConstants.JWT_ISSUER)
						.subject(JacksonHelper.toJson(subject))
						.expirationTime(expirationTime)
						.issuedAt(now)
						.jwtId(UUID.randomUUID().toString())
						.build()
				)
				.additionalClaims(Map.of(JwtUtils.Payload.TYPE, type))
				.build()
		);
	}

	String generateAccessToken(Subject subject, Instant now) {
		return this.generateJWT(subject, Date.from(now), Date.from(now.plusSeconds(AuthConstants.ACCESS_TOKEN_EXPIRATION_TIME)), AuthConstants.ACCESS_TOKEN);
	}

	String generateRefreshToken(Subject subject, Instant now) {
		return this.generateJWT(subject, Date.from(now), Date.from(now.plusSeconds(AuthConstants.REFRESH_TOKEN_EXPIRATION_TIME)), AuthConstants.REFRESH_TOKEN);
	}

	Subject extractSubject(String jwt, String type) {
		try {
			return JacksonHelper.fromJson(this.jwtUtils.getPayloads(jwt, type).getSubject(), Subject.class);
		} catch (IllegalArgumentException e) {
			throw new TiTiAuthException(TiTiAuthBusinessCodes.REISSUE_ACCESS_TOKEN_FAILURE_INVALID_REFRESH_TOKEN);
		}
	}

	@Builder
	record Subject(
		String memberId,
		String deviceId
	) {

		public String generateHashValue() {
			return HashingUtils.hashSha256(this.memberId, this.deviceId);
		}

	}

}
