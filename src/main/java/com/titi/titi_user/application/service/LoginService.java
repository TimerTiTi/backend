package com.titi.titi_user.application.service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.titi.titi_common_lib.util.JwtUtils;
import com.titi.titi_user.application.common.constant.UserConstants;
import com.titi.titi_user.application.port.in.LoginUseCase;
import com.titi.titi_user.application.port.out.persistence.FindMemberPort;
import com.titi.titi_user.common.TiTiUserBusinessCodes;
import com.titi.titi_user.common.TiTiUserException;
import com.titi.titi_user.domain.member.Member;

@Service
@RequiredArgsConstructor
class LoginService implements LoginUseCase {

	private final FindMemberPort findMemberPort;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	@Value("${crypto.secret-key}")
	private String secretKey;

	@Override
	public Result invoke(Command command) {
		final Member member = this.findMemberPort.invoke(Member.builder().username(command.username()).build())
			.orElseThrow(() -> new TiTiUserException(TiTiUserBusinessCodes.LOGIN_FAILURE_MISMATCHED_MEMBER_INFORMATION));
		final String rawPassword = command.encodedEncryptedPassword().getRawPassword(this.secretKey.getBytes());
		this.validatePassword(rawPassword, member);
		final Instant now = Instant.now();
		return Result.builder()
			.accessToken(this.generateAccessToken(member, now))
			.refreshToken(this.generateRefreshToken(member, now))
			.build();
	}

	private void validatePassword(String rawPassword, Member member) {
		if (!this.passwordEncoder.matches(rawPassword, member.password())) {
			throw new TiTiUserException(TiTiUserBusinessCodes.LOGIN_FAILURE_MISMATCHED_MEMBER_INFORMATION);
		}
	}

	private String generateAccessToken(Member member, Instant now) {
		return this.generateJWT(member.id().toString(), Date.from(now), Date.from(now.plusSeconds(UserConstants.ACCESS_TOKEN_EXPIRATION_TIME)), UserConstants.ACCESS_TOKEN);
	}

	private String generateRefreshToken(Member member, Instant now) {
		return this.generateJWT(member.id().toString(), Date.from(now), Date.from(now.plusSeconds(UserConstants.REFRESH_TOKEN_EXPIRATION_TIME)), UserConstants.REFRESH_TOKEN);
	}

	private String generateJWT(String subject, Date now, Date expirationTime, String type) {
		return this.jwtUtils.generate(
			JwtUtils.Payload.builder()
				.registeredClaim(
					JwtUtils.Payload.RegisteredClaim.builder()
						.issuer(UserConstants.JWT_ISSUER)
						.subject(subject)
						.expirationTime(expirationTime)
						.issuedAt(now)
						.jwtId(UUID.randomUUID().toString())
						.build()
				)
				.additionalClaims(Map.of(JwtUtils.Payload.TYPE, type))
				.build()
		);
	}

}
