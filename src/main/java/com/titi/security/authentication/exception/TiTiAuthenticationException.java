package com.titi.security.authentication.exception;

import java.util.List;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

import com.titi.exception.TiTiErrorCodes;
import com.titi.titi_common_lib.dto.ErrorResponse.FieldError;

@Getter
public class TiTiAuthenticationException extends AuthenticationException {

	private final TiTiErrorCodes errorCode = TiTiErrorCodes.AUTHENTICATION_FAILURE;
	private final List<FieldError> errors;

	public TiTiAuthenticationException(List<FieldError> errors) {
		super(TiTiErrorCodes.AUTHENTICATION_FAILURE.getMessage());
		this.errors = errors;
	}

}
