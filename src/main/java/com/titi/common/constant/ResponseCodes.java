package com.titi.common.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseCodes {

	@Getter
	@AllArgsConstructor
	public enum ResultCode {

		SUCCESS(200, "R1000", "Success.")
		;

		private final int status;
		private final String code;
		private final String message;

	}

	@Getter
	@AllArgsConstructor
	public enum ErrorCode {

		INPUT_VALUE_INVALID(400, "E9000", "Invalid input value."),
		INPUT_TYPE_INVALID(400, "E9001", "Invalid input type."),
		METHOD_NOT_ALLOWED(405, "E9002", "HTTP method not allowed."),
		HTTP_MESSAGE_NOT_READABLE(400, "E9003", "HTTP request body format is invalid."),
		REQUEST_PARAMETER_MISSING(400, "E9004", "Request parameter is missing."),
		REQUEST_HEADER_MISSING(400, "E9005", "Request header is missing."),
		AUTHENTICATION_FAILURE(401, "E9006", "Authentication failed."),
		INSUFFICIENT_AUTHORITY(403, "E9007", "Insufficient authority or permissions."),
		INTERNAL_SERVER_ERROR(500, "E9999", "Internal server error.");

		private final int status;
		private final String code;
		private final String message;

	}

}
