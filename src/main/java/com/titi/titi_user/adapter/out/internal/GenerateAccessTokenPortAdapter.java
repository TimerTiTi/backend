package com.titi.titi_user.adapter.out.internal;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.adapter.in.internal.GenerateAccessTokenGateway;
import com.titi.titi_user.application.port.out.internal.GenerateAccessTokenPort;

@Component
@RequiredArgsConstructor
class GenerateAccessTokenPortAdapter implements GenerateAccessTokenPort {

	private final GenerateAccessTokenGateway generateAccessTokenGateway;

	@Override
	public Result invoke(Command command) {
		final GenerateAccessTokenGateway.GenerateAccessTokenResponse response = this.generateAccessTokenGateway.invoke(
			GenerateAccessTokenGateway.GenerateAccessTokenRequest.builder()
				.memberId(command.memberId())
				.deviceId(command.deviceId())
				.build()
		);
		return Result.builder()
			.accessToken(response.accessToken())
			.refreshToken(response.refreshToken())
			.build();
	}

}
