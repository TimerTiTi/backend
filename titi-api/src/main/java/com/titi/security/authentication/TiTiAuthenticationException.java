package com.titi.security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

import com.titi.common.ResponseCodes.ErrorCode;
import com.titi.exception.ErrorResponse;

@Getter
public class TiTiAuthenticationException extends AuthenticationException {

	private final ErrorCode errorCode;
	private final List<ErrorResponse.FieldError> errors;

	public TiTiAuthenticationException(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.errors = errors;
	}

	public TiTiAuthenticationException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.errors = new ArrayList<>();
	}

}
