package com.titi.titi_user.application.port.out.auth;

import lombok.Builder;

public interface CreateAccountPort {

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
