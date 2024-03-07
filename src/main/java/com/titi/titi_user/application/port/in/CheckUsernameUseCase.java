package com.titi.titi_user.application.port.in;

import lombok.Builder;

public interface CheckUsernameUseCase {

	Result invoke(Command command);

	@Builder
	record Command(
		String username
	) {

	}

	@Builder
	record Result(
		boolean isPresent
	) {

	}

}
