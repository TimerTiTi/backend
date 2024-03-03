package com.titi.titi_auth.application.port.in;

import static com.titi.titi_common_lib.constant.Constants.*;

import lombok.Builder;

public interface VerifyAuthCodeUseCase {

	int AUTH_TOKEN_EXPIRATION_TIME = 30 * 60 * SECONDS;

	Result invoke(Command command);

	@Builder
	record Command(
		String authKey,
		String authCode
	) {

	}

	@Builder
	record Result(
		String authToken
	) {

	}

}
