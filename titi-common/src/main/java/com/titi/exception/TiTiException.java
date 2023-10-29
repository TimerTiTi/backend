package com.titi.exception;

import com.titi.common.ApiCodes;

import lombok.Getter;

@Getter
public abstract class TiTiException extends RuntimeException {

	private final ApiCodes.ErrorCode errorCode;

	public TiTiException(ApiCodes.ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

}
