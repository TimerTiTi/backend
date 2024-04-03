package com.titi.titi_user.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.titi.titi_user.application.port.in.LoginUseCase;
import com.titi.titi_user.application.port.out.internal.GenerateAccessTokenPort;
import com.titi.titi_user.application.port.out.persistence.FindMemberPort;
import com.titi.titi_user.common.TiTiUserBusinessCodes;
import com.titi.titi_user.common.TiTiUserException;
import com.titi.titi_user.domain.member.Member;

@Service
@RequiredArgsConstructor
class LoginService implements LoginUseCase {

	private final FindMemberPort findMemberPort;
	private final PasswordEncoder passwordEncoder;
	private final GenerateAccessTokenPort generateAccessTokenPort;
	@Value("${crypto.secret-key}")
	private String secretKey;

	@Override
	public Result invoke(Command command) {
		final Member member = this.findMemberPort.invoke(Member.builder().username(command.username()).build())
			.orElseThrow(() -> new TiTiUserException(TiTiUserBusinessCodes.LOGIN_FAILURE_MISMATCHED_MEMBER_INFORMATION));
		final String rawPassword = command.encodedEncryptedPassword().getRawPassword(this.secretKey.getBytes());
		this.validatePassword(rawPassword, member);
		final GenerateAccessTokenPort.Result result = this.generateAccessTokenPort.invoke(
			GenerateAccessTokenPort.Command.builder()
				.memberId(String.valueOf(member.id()))
				.deviceId(command.deviceId())
				.build()
		);
		return Result.builder()
			.accessToken(result.accessToken())
			.refreshToken(result.refreshToken())
			.build();
	}

	private void validatePassword(String rawPassword, Member member) {
		if (!this.passwordEncoder.matches(rawPassword, member.password())) {
			throw new TiTiUserException(TiTiUserBusinessCodes.LOGIN_FAILURE_MISMATCHED_MEMBER_INFORMATION);
		}
	}

}
