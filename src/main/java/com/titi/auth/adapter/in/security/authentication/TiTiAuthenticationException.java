package com.titi.auth.adapter.in.security.authentication;

import static com.titi.common.constant.ResponseCodes.ErrorCode.*;

import java.util.List;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

import com.titi.common.constant.ResponseCodes.ErrorCode;
import com.titi.common.dto.ErrorResponse.FieldError;

@Getter
public class TiTiAuthenticationException extends AuthenticationException {

	private final ErrorCode errorCode = AUTHENTICATION_FAILURE;
	private final List<FieldError> errors;

	public TiTiAuthenticationException(List<FieldError> errors) {
		super(AUTHENTICATION_FAILURE.getMessage());
		this.errors = errors;
	}

}