package com.titi.titi_common_lib.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.titi.exception.TiTiErrorCodes;

@Getter
public class ErrorResponse {

	private final String code;
	private final String message;
	private final List<FieldError> errors;

	private ErrorResponse(String code, String message, List<FieldError> errors) {
		this.code = code;
		this.message = message;
		this.errors = errors;
	}

	private ErrorResponse(TiTiErrorCodes errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.errors = new ArrayList<>();
	}

	public static ErrorResponse of(TiTiErrorCodes errorCode) {
		return new ErrorResponse(errorCode);
	}

	public static ErrorResponse of(TiTiErrorCodes errorCode, List<FieldError> errors) {
		return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), errors);
	}

	public static ErrorResponse of(String code, String message, List<FieldError> errors) {
		return new ErrorResponse(code, message, errors);
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

		public static List<FieldError> of(String field, String value, TiTiErrorCodes errorCode) {
			final List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(field, value, errorCode.getMessage()));
			return fieldErrors;
		}

	}

}
