package com.titi.security.authentication.exception;

import static com.titi.titi_common_lib.constant.TiTiErrorCode.*;

import java.util.List;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

import com.titi.titi_common_lib.constant.TiTiErrorCode;
import com.titi.titi_common_lib.dto.ErrorResponse.FieldError;

@Getter
public class TiTiAuthenticationException extends AuthenticationException {

	private final TiTiErrorCode errorCode = AUTHENTICATION_FAILURE;
	private final List<FieldError> errors;

	public TiTiAuthenticationException(List<FieldError> errors) {
		super(AUTHENTICATION_FAILURE.getMessage());
		this.errors = errors;
	}

}
