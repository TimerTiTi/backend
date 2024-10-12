package com.titi.titi_user.application.port.out.auth;

import lombok.Builder;

public interface GenerateAccessTokenPort {

	Result invoke(Command command);

	@Builder
	record Command(
		String memberId,
		String deviceId
	) {

	}

	@Builder
	record Result(
		String accessToken,
		String refreshToken
	) {

	}

}
