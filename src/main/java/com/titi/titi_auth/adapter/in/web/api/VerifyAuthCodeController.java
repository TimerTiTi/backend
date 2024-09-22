package com.titi.titi_auth.adapter.in.web.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.port.in.VerifyAuthCodeUseCase;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;

@RestController
@RequiredArgsConstructor
public class VerifyAuthCodeController implements AuthApi {

	private final VerifyAuthCodeUseCase verifyAuthCodeUseCase;

	@Operation(summary = "verifyAuthCode API", description = "Verify the authentication code.")
	@PostMapping(value = "/code/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VerifyAuthCodeResponseBody> verifyAuthCode(@Valid @RequestBody VerifyAuthCodeRequestBody requestBody) {
		final VerifyAuthCodeUseCase.Result result = this.verifyAuthCodeUseCase.invoke(
			VerifyAuthCodeUseCase.Command.builder()
				.authKey(requestBody.authKey())
				.authCode(requestBody.authCode())
				.build()
		);
		return ResponseEntity.status(TiTiAuthBusinessCodes.VERIFY_AUTH_CODE_SUCCESS.getStatus())
			.body(
				VerifyAuthCodeResponseBody.builder()
					.code(TiTiAuthBusinessCodes.VERIFY_AUTH_CODE_SUCCESS.getCode())
					.message(TiTiAuthBusinessCodes.VERIFY_AUTH_CODE_SUCCESS.getMessage())
					.authToken(result.authToken())
					.build()
			);
	}

	@Builder
	public record VerifyAuthCodeRequestBody(
		@Schema(
			description = "The key required to validate the authentication code.",
			requiredMode = Schema.RequiredMode.REQUIRED
		) @NotBlank String authKey,
		@Schema(
			description = "The authentication code issued from the server.",
			requiredMode = Schema.RequiredMode.REQUIRED
		) @NotBlank String authCode
	) {

	}

	@Builder
	public record VerifyAuthCodeResponseBody(
		@Schema(
			description = "TiTi Business code."
		) String code,
		@Schema(
			description = "API result message."
		) String message,
		@Schema(
			description = "The token issued upon successful authentication code validation."
		) String authToken
	) {

	}

}
