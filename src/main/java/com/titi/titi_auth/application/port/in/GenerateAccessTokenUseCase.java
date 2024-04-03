package com.titi.titi_auth.application.port.in;

import lombok.Builder;

public interface GenerateAccessTokenUseCase {

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
