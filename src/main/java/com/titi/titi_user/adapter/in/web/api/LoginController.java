package com.titi.titi_user.adapter.in.web.api;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import com.titi.titi_common_lib.util.HttpRequestHeaderParser;
import com.titi.titi_user.application.port.in.LoginUseCase;
import com.titi.titi_user.common.TiTiUserBusinessCodes;
import com.titi.titi_user.domain.member.EncodedEncryptedPassword;

@RestController
@RequiredArgsConstructor
class LoginController implements UserApi {

	private final LoginUseCase loginUseCase;

	@Operation(summary = "login API", description = "You have successfully logged in.")
	@PostMapping(value = "/members/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginResponseBody> login(
		@Parameter(example = "mozilla/5.0 (iphone; cpu iphone os 15_0_1 like mac os x) applewebkit/605.1.15 (khtml, like gecko) version/15.0 mobile/15e148 safari/604.1")
		@RequestHeader(name = "User-Agent", required = false) String userAgent,
		@Valid @RequestBody LoginRequestBody requestBody
	) {
		final LoginUseCase.Result result = this.loginUseCase.invoke(
			LoginUseCase.Command.builder()
				.username(requestBody.username())
				.encodedEncryptedPassword(EncodedEncryptedPassword.builder().value(requestBody.encodedEncryptedPassword()).build())
				.deviceId(requestBody.deviceId())
				.deviceType(userAgent == null ? null : HttpRequestHeaderParser.parseSystemInformationFromUserAgent(userAgent))
				.build()
		);
		return ResponseEntity.status(TiTiUserBusinessCodes.LOGIN_SUCCESS.getStatus())
			.body(
				LoginResponseBody.builder()
					.code(TiTiUserBusinessCodes.LOGIN_SUCCESS.getCode())
					.message(TiTiUserBusinessCodes.LOGIN_SUCCESS.getMessage())
					.accessToken(result.accessToken())
					.refreshToken(result.refreshToken())
					.deviceId(result.deviceId())
					.build()
			);
	}

	@Builder
	public record LoginRequestBody(
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
			description = "An identifier that distinguishes the device. If null, a new deviceId is issued."
		) @Nullable String deviceId
	) {

	}

	@Builder
	public record LoginResponseBody(
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
		) String refreshToken,
		@Schema(
			description = "An identifier that distinguishes the device. deviceId is included only if it has been newly issued."
		) @Nullable String deviceId
	) {

	}

}
