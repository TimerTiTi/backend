package com.titi.titi_user.adapter.out.internal;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.port.in.GenerateAccessTokenUseCase;
import com.titi.titi_user.application.port.out.internal.GenerateAccessTokenPort;

@Component
@RequiredArgsConstructor
class GenerateAccessTokenPortAdapter implements GenerateAccessTokenPort {

	private final GenerateAccessTokenUseCase generateAccessTokenUseCase;

	@Override
	public Result invoke(Command command) {
		final GenerateAccessTokenUseCase.Result result = this.generateAccessTokenUseCase.invoke(
			GenerateAccessTokenUseCase.Command.builder()
				.memberId(command.memberId())
				.deviceId(command.deviceId())
				.build()
		);
		return Result.builder()
			.accessToken(result.accessToken())
			.refreshToken(result.refreshToken())
			.build();
	}

}
