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
	REISSUE_ACCESS_TOKEN_SUCCESS(200, "AU1004", "Successfully reissued the Access Token."),
	REISSUE_ACCESS_TOKEN_FAILURE_INVALID_REFRESH_TOKEN(200, "AU1005", "The Refresh Token is invalid, so the reissuance of the Access Token has failed."),

	GENERATE_AUTH_CODE_FAILURE(500, "AU7000", "Failed to generate and transmit the authentication code. Please try again later."),
	VERIFY_AUTH_CODE_FAILURE(500, "AU7001", "Authentication code verification failed. Please try again later."),
	CACHE_SERVER_ERROR(500, "AU9000", "Cache server error. Please try again later"),
	;

	private final int status;
	private final String code;
	private final String message;

}
