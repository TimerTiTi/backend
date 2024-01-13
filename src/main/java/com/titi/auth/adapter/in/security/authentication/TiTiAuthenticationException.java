package com.titi.auth.adapter.in.security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

import com.titi.common.constant.ResponseCodes.ErrorCode;
import com.titi.common.dto.ErrorResponse.FieldError;

@Getter
public class TiTiAuthenticationException extends AuthenticationException {

	private final ErrorCode errorCode;
	private final List<FieldError> errors;

	public TiTiAuthenticationException(ErrorCode errorCode, List<FieldError> errors) {
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
