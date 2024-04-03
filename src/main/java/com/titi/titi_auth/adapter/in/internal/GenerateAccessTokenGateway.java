package com.titi.titi_auth.adapter.in.internal;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.adapter.in.internal.mapper.AuthInternalMapper;
import com.titi.titi_auth.application.port.in.GenerateAccessTokenUseCase;

@Validated
@Component
@RequiredArgsConstructor
public class GenerateAccessTokenGateway {

	private final GenerateAccessTokenUseCase generateAccessTokenUseCase;

	public GenerateAccessTokenResponse invoke(@Valid GenerateAccessTokenRequest request) {
		final GenerateAccessTokenUseCase.Result result = this.generateAccessTokenUseCase.invoke(AuthInternalMapper.INSTANCE.toCommand(request));
		return AuthInternalMapper.INSTANCE.toResponse(result);
	}

	@Builder
	public record GenerateAccessTokenRequest(
		@NotBlank String memberId,
		@NotBlank String deviceId
	) {

	}

	@Builder
	public record GenerateAccessTokenResponse(
		String accessToken,
		String refreshToken
	) {

	}

}
