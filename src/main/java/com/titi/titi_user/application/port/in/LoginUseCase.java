package com.titi.titi_user.application.port.in;

import lombok.Builder;

import com.titi.titi_user.domain.member.EncodedEncryptedPassword;

public interface LoginUseCase {

	Result invoke(Command command);

	@Builder
	record Command(
		String username,
		EncodedEncryptedPassword encodedEncryptedPassword
	) {

	}

	@Builder
	record Result(
		String accessToken,
		String refreshToken
	) {

	}

}
