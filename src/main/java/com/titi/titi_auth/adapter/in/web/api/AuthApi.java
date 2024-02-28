package com.titi.titi_auth.adapter.in.web.api;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Tag(name = "Auth API")
@RequestMapping(value = "/api/auth")
interface AuthApi {

	@Getter
	@AllArgsConstructor
	enum ResultCode {
		GENERATE_AUTH_CODE_SUCCESS(201, "AU1000", "Successfully generated and transmitted the authentication code."),
		VERIFY_AUTH_CODE_SUCCESS(200, "AU1001", "Successfully verifying the authentication code issues an authentication token."),
		;

		private final int status;
		private final String code;
		private final String message;
	}

}
