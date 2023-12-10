package com.titi.exception;

import java.util.ArrayList;
import java.util.List;

import com.titi.common.ResponseCodes;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ErrorResponse {

	private final int status;
	private final String code;
	private final String message;
	private final List<FieldError> errors;

	private ErrorResponse(ResponseCodes.ErrorCode errorCode, List<FieldError> errors) {
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.errors = errors;
	}

	private ErrorResponse(ResponseCodes.ErrorCode errorCode) {
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.errors = new ArrayList<>();
	}

	public static ErrorResponse of(final ResponseCodes.ErrorCode errorCode) {
		return new ErrorResponse(errorCode);
	}

	public static ErrorResponse of(final ResponseCodes.ErrorCode errorCode, final List<FieldError> errors) {
		return new ErrorResponse(errorCode, errors);
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class FieldError {

		private String field;
		private String value;
		private String reason;

		public FieldError(String field, String value, String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}

		public static List<FieldError> of(String field, String value, String reason) {
			final List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(field, value, reason));
			return fieldErrors;
		}

		public static List<FieldError> of(String field, String value, ResponseCodes.ErrorCode errorCode) {
			final List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(field, value, errorCode.getMessage()));
			return fieldErrors;
		}

	}

}
