package com.titi.titi_auth.adapter.in.web.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import com.titi.titi_auth.application.port.in.CheckUsernameUseCase;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;

@Validated
@RestController
@RequiredArgsConstructor
public class CheckUsernameController implements AuthApi {

	private final CheckUsernameUseCase checkUsernameUseCase;

	@Operation(summary = "checkUsername API", description = "Check if the username exists.")
	@Parameter(name = "username", description = "Username in Email format.", required = true)
	@GetMapping(value = "/members/check", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CheckUsernameResponseBody> checkUsername(@NotBlank @Email @Length(max = 30) @RequestParam String username) {
		final CheckUsernameUseCase.Result result = this.checkUsernameUseCase.invoke(CheckUsernameUseCase.Command.builder().username(username).build());
		final TiTiAuthBusinessCodes businessCodes = result.isPresent() ? TiTiAuthBusinessCodes.ALREADY_EXISTS_USERNAME : TiTiAuthBusinessCodes.DOES_NOT_EXIST_USERNAME;
		return ResponseEntity.status(businessCodes.getStatus())
			.body(
				CheckUsernameResponseBody.builder()
					.code(businessCodes.getCode())
					.message(businessCodes.getMessage())
					.isPresent(result.isPresent())
					.build()
			);
	}

	@Builder
	public record CheckUsernameResponseBody(
		@Schema(
			description = "TiTi Business code."
		) String code,
		@Schema(
			description = "API result message."
		) String message,
		@Schema(
			description = "Existence of the username."
		) boolean isPresent
	) {

	}

}
