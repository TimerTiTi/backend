package com.titi.titi_auth.adapter.in.web.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.port.in.ReissueAccessTokenUseCase;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;

@RestController
@RequiredArgsConstructor
class ReissueAccessTokenController implements AuthApi {

	private final ReissueAccessTokenUseCase reissueAccessTokenUseCase;

	@Operation(summary = "Reissue access token API", description = "Reissue the Access Token.")
	@PostMapping(value = "/token/reissue", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReissueAccessTokenResponseBody> reissueAccessToken(@Valid @RequestBody ReissueAccessTokenRequestBody requestBody) {
		final ReissueAccessTokenUseCase.Result result = this.reissueAccessTokenUseCase.invoke(
			ReissueAccessTokenUseCase.Command.builder()
				.refreshToken(requestBody.refreshToken())
				.build()
		);
		return ResponseEntity.status(TiTiAuthBusinessCodes.REISSUE_ACCESS_TOKEN_SUCCESS.getStatus())
			.body(
				ReissueAccessTokenResponseBody.builder()
					.code(TiTiAuthBusinessCodes.REISSUE_ACCESS_TOKEN_SUCCESS.getCode())
					.message(TiTiAuthBusinessCodes.REISSUE_ACCESS_TOKEN_SUCCESS.getMessage())
					.accessToken(result.accessToken())
					.refreshToken(result.refreshToken())
					.build()
			);
	}

	@Builder
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public record ReissueAccessTokenRequestBody(
		@Schema(
			description = "Refresh Token for reissuing the Access Token (Validity : 2 weeks)",
			requiredMode = Schema.RequiredMode.REQUIRED
		) @NotBlank String refreshToken
	) {

	}

	@Builder
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public record ReissueAccessTokenResponseBody(
		@Schema(
			description = "TiTi Business code."
		) String code,
		@Schema(
			description = "API result message."
		) String message,
		@Schema(
			description = "Access Token for self-authentication (Validity : 30 minutes)"
		) String accessToken,
		@Schema(
			description = "Refresh Token for reissuing the Access Token (Validity : 2 weeks)"
		) String refreshToken
	) {

	}

}
