package com.titi.exception;

import java.util.ArrayList;
import java.util.List;

import com.titi.common.ResponseCodes;

import lombok.Getter;

@Getter
public class TiTiException extends RuntimeException {

	private final ResponseCodes.ErrorCode errorCode;
	private final List<ErrorResponse.FieldError> errors;

	public TiTiException(ResponseCodes.ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.errors = errors;
	}

	public TiTiException(ResponseCodes.ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.errors = new ArrayList<>();
	}

}
