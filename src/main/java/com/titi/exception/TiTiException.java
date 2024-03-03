package com.titi.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import com.titi.titi_common_lib.dto.ErrorResponse;

@Getter
public class TiTiException extends RuntimeException {

	private final int status;
	private final String code;
	private final String message;
	private final List<ErrorResponse.FieldError> errors;

	public TiTiException(int status, String code, String message, List<ErrorResponse.FieldError> errors) {
		super(message);
		this.status = status;
		this.code = code;
		this.message = message;
		this.errors = errors;
	}

	public TiTiException(int status, String code, String message) {
		super(message);
		this.status = status;
		this.code = code;
		this.message = message;
		this.errors = new ArrayList<>();
	}


	public TiTiException(TiTiErrorCodes errorCodes, List<ErrorResponse.FieldError> errors) {
		super(errorCodes.getMessage());
		this.status = errorCodes.getStatus();
		this.code = errorCodes.getCode();
		this.message = errorCodes.getMessage();
		this.errors = errors;
	}

	public TiTiException(TiTiErrorCodes errorCodes) {
		super(errorCodes.getMessage());
		this.status = errorCodes.getStatus();
		this.code = errorCodes.getCode();
		this.message = errorCodes.getMessage();
		this.errors = new ArrayList<>();
	}

}
