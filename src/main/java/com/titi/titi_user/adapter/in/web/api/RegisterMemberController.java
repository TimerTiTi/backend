package com.titi.titi_user.adapter.in.web.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import com.titi.titi_user.application.port.in.RegisterMemberUseCase;
import com.titi.titi_user.common.TiTiUserBusinessCodes;

@RestController
@RequiredArgsConstructor
class RegisterMemberController implements UserApi {

	private final RegisterMemberUseCase registerMemberUseCase;

	@Operation(summary = "registerMember API", description = "Sign up for the TiTi service as a regular member.")
	@PostMapping(value = "/members/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisterMemberResponseBody> registerMember(@Valid @RequestBody RegisterMemberRequestBody requestBody) {
		this.registerMemberUseCase.invoke(
			RegisterMemberUseCase.Command.builder()
				.username(requestBody.username())
				.encodedEncryptedPassword(requestBody.encodedEncryptedPassword())
				.nickname(requestBody.nickname())
				.authToken(requestBody.authToken())
				.build()
		);
		return ResponseEntity.status(TiTiUserBusinessCodes.REGISTER_MEMBER_SUCCESS.getStatus())
			.body(
				RegisterMemberResponseBody.builder()
					.code(TiTiUserBusinessCodes.REGISTER_MEMBER_SUCCESS.getCode())
					.message(TiTiUserBusinessCodes.REGISTER_MEMBER_SUCCESS.getMessage())
					.build()
			);
	}

	@Builder
	public record RegisterMemberRequestBody(
		@Schema(
			description = "username in Email format.",
			requiredMode = Schema.RequiredMode.REQUIRED
		) @NotBlank @Email @Length(max = 30) String username,
		@Schema(
			description = "it is encrypted using AES-256 to wrap the raw_password then encoded using base64url.",
			requiredMode = Schema.RequiredMode.REQUIRED,
			example = "6o171NOMWMJ2BMgouXrOr82lFLFFo-hA9qphcA=="
		) @NotBlank String encodedEncryptedPassword,
		@Schema(
			description = "nickname to be used for the TiTi service.",
			requiredMode = Schema.RequiredMode.REQUIRED
		) @NotBlank @Length(max = 15) String nickname,
		@Schema(
			description = "The token issued upon successful authentication code validation.",
			requiredMode = Schema.RequiredMode.REQUIRED
		) @NotBlank String authToken
	) {

	}

	@Builder
	public record RegisterMemberResponseBody(
		@Schema(
			description = "TiTi Business code."
		) String code,
		@Schema(
			description = "API result message."
		) String message
	) {

	}

}
