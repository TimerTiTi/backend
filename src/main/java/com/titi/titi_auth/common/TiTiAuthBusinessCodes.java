package com.titi.titi_auth.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TiTiAuthBusinessCodes {
	GENERATE_AUTH_CODE_SUCCESS(201, "AU1000", "Successfully generated and transmitted the authentication code."),
	VERIFY_AUTH_CODE_SUCCESS(200, "AU1001", "Successfully verifying the authentication code issues an authentication token."),
	INVALID_AUTH_CODE(200, "AU1002", "The authentication code has expired or is invalid."),
	MISMATCHED_AUTH_CODE(200, "AU1003", "The authentication code does not match."),
	;

	private final int status;
	private final String code;
	private final String message;
}
