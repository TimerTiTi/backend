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
		GENERATE_AUTH_CODE_FAILURE(200, "AU1001", "Failed to generate and transmit the authentication code. Please try again later."),
		;

		private final int status;
		private final String code;
		private final String message;
	}

}
