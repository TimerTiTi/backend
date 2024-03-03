package com.titi.titi_auth.application.port.out;

import java.util.Optional;

import lombok.Builder;

import com.titi.titi_auth.domain.AuthCode;

public interface GetAuthCodePort {

	Result invoke(Command command);

	@Builder
	record Command(
		String authKey
	) {

	}

	@Builder
	record Result(
		Optional<AuthCode> authCode
	) {

	}

}
