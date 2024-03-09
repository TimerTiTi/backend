package com.titi.titi_user.common;

import java.util.List;

import com.titi.exception.TiTiException;
import com.titi.titi_common_lib.dto.ErrorResponse;

public class TiTiUserException extends TiTiException {

	public TiTiUserException(TiTiUserBusinessCodes businessCodes, List<ErrorResponse.FieldError> errors) {
		super(businessCodes.getStatus(), businessCodes.getCode(), businessCodes.getMessage(), errors);
	}

	public TiTiUserException(TiTiUserBusinessCodes businessCodes) {
		super(businessCodes.getStatus(), businessCodes.getCode(), businessCodes.getMessage());
	}

}
