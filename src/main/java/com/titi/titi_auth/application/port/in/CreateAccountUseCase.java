package com.titi.titi_auth.application.port.in;

import lombok.Builder;

public interface CreateAccountUseCase {

	Result invoke(Command command);

	@Builder
	record Command(
		String username,
		String encodedEncryptedPassword
	) {

	}

	@Builder
	record Result(
		Long accountId
	) {

	}

}
