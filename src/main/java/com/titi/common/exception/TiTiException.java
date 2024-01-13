package com.titi.common.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import com.titi.common.constant.ResponseCodes.ErrorCode;
import com.titi.common.dto.ErrorResponse.FieldError;

@Getter
public class TiTiException extends RuntimeException {

	private final ErrorCode errorCode;
	private final List<FieldError> errors;

	public TiTiException(ErrorCode errorCode, List<FieldError> errors) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.errors = errors;
	}

	public TiTiException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.errors = new ArrayList<>();
	}

}
