package com.titi.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import com.titi.titi_common_lib.constant.TiTiErrorCode;
import com.titi.titi_common_lib.dto.ErrorResponse;

@Getter
public class TiTiException extends RuntimeException {

	private final TiTiErrorCode errorCode;
	private final List<ErrorResponse.FieldError> errors;

	public TiTiException(TiTiErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.errors = errors;
	}

	public TiTiException(TiTiErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.errors = new ArrayList<>();
	}

}
