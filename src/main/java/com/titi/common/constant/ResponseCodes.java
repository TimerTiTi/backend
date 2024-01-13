package com.titi.common.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseCodes {

	@Getter
	@AllArgsConstructor
	public enum SuccessCode {

		;

		private final int status;
		private final String code;
		private final String message;

	}

	@Getter
	@AllArgsConstructor
	public enum ErrorCode {

		// Global
		INTERNAL_SERVER_ERROR(500, "EG001", "Internal server error."),
		INPUT_VALUE_INVALID(400, "EG002", "Invalid input value."),
		INPUT_TYPE_INVALID(400, "EG003", "Invalid input type."),
		METHOD_NOT_ALLOWED(405, "EG004", "HTTP method not allowed."),
		HTTP_MESSAGE_NOT_READABLE(400, "EG005", "HTTP request body format is invalid."),
		REQUEST_PARAMETER_MISSING(400, "EG006", "Request parameter is missing."),
		REQUEST_HEADER_MISSING(400, "EG007", "Request header is missing."),

		// Authorization
		AUTHENTICATION_FAILURE(401, "E-A001", "Authentication failed."),
		INSUFFICIENT_AUTHORITY(403, "E-A002", "Insufficient authority or permissions.")

		;

		private final int status;
		private final String code;
		private final String message;

	}

}
