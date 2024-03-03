package com.titi.titi_auth.common;

import java.util.List;

import com.titi.exception.TiTiException;
import com.titi.titi_common_lib.dto.ErrorResponse;

public class TiTiAuthException extends TiTiException {

	public TiTiAuthException(TiTiAuthBusinessCodes businessCodes, List<ErrorResponse.FieldError> errors) {
		super(businessCodes.getStatus(), businessCodes.getCode(), businessCodes.getMessage(), errors);
	}

	public TiTiAuthException(TiTiAuthBusinessCodes businessCodes) {
		super(businessCodes.getStatus(), businessCodes.getCode(), businessCodes.getMessage());
	}

}
