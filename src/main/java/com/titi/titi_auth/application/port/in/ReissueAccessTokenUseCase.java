package com.titi.titi_auth.application.port.in;

import lombok.Builder;

public interface ReissueAccessTokenUseCase {

	Result invoke(Command command);

	@Builder
	record Command(
		String refreshToken
	) {

	}

	@Builder
	record Result(
		String accessToken,
		String refreshToken
	) {

	}

}
