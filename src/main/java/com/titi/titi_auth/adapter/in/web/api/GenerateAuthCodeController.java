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
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.port.in.GenerateAuthCodeUseCase;
import com.titi.titi_auth.domain.AuthenticationType;
import com.titi.titi_auth.domain.TargetType;

@RestController
@RequiredArgsConstructor
class GenerateAuthCodeController implements AuthApi {

	private final GenerateAuthCodeUseCase generateAuthCodeUseCase;

	@Operation(summary = "generateAuthCode API", description = "Generate AuthCode and send it to targetValue.")
	@PostMapping(value = "/code", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenerateAuthCodeResponseBody> generateAuthCode(@Valid @RequestBody GenerateAuthCodeRequestBody requestBody) {
		return this.makeResponseEntity(
			switch (requestBody.targetType()) {
				case TargetType.EMAIL -> this.generateAuthCodeForEmail(requestBody);
			}
		);
	}

	private boolean generateAuthCodeForEmail(GenerateAuthCodeRequestBody requestBody) {
		return this.generateAuthCodeUseCase.invoke(
			GenerateAuthCodeUseCase.Command.ToEmail.builder()
				.authType(requestBody.authType)
				.email(requestBody.targetValue())
				.build()
		);
	}

	private ResponseEntity<GenerateAuthCodeResponseBody> makeResponseEntity(boolean isSuccessful) {
		final ResultCode resultCode = isSuccessful ? ResultCode.GENERATE_AUTH_CODE_SUCCESS : ResultCode.GENERATE_AUTH_CODE_FAILURE;
		return ResponseEntity.status(resultCode.getStatus())
			.body(
				GenerateAuthCodeResponseBody.builder()
					.code(resultCode.getCode())
					.message(resultCode.getMessage())
					.build()
			);
	}

	@Builder
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public record GenerateAuthCodeRequestBody(
		@Schema(
			description = "Type of recipient for the issued authentication code.",
			requiredMode = Schema.RequiredMode.REQUIRED
		) @NotNull TargetType targetType,
		@Schema(
			description = "Recipient for the issued authentication code.",
			requiredMode = Schema.RequiredMode.REQUIRED
		) @NotBlank String targetValue,
		@Schema(
			description = "Type of authentication code issuance.",
			requiredMode = Schema.RequiredMode.REQUIRED
		) @NotNull AuthenticationType authType
	) {

	}

	@Builder
	@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
	public record GenerateAuthCodeResponseBody(
		@Schema(
			description = "TiTi Business code."
		) String code,
		@Schema(
			description = "API result message."
		) String message
	) {

	}

}
