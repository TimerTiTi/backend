package com.titi.library.titi_common.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import com.titi.library.titi_common.constant.ResponseCodes;
import com.titi.library.titi_common.dto.ErrorResponse;

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
