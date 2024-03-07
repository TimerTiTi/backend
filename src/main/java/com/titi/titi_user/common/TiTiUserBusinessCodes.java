package com.titi.titi_user.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TiTiUserBusinessCodes {

	DOES_NOT_EXIST_USERNAME(200, "USR1000", "The username does not exist."),
	ALREADY_EXISTS_USERNAME(200, "USR1001", "The username already exists."),

	;

	private final int status;
	private final String code;
	private final String message;

}
