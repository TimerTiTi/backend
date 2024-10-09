package com.titi.titi_user.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TiTiUserBusinessCodes {

	REGISTER_MEMBER_SUCCESS(200, "USR1002", "Successfully completed the regular membership registration."),
	REGISTER_MEMBER_FAILURE_INVALID_AUTH_TOKEN(200, "USR1003", "The registration has failed due to an invalid Auth Token."),
	REGISTER_MEMBER_FAILURE_ALREADY_EXISTS_USERNAME(200, "USR1004", "The registration has failed as the username already exists."),
	LOGIN_SUCCESS(200, "USR1005", "You have successfully logged in"),
	LOGIN_FAILURE_MISMATCHED_MEMBER_INFORMATION(200, "USR1006", "Login failed due to mismatched member information."),

	AUTH_KEY_MISMATCHED_REGISTRATION_INFORMATION(400, "USR7000", "The authentication key does not match the registration information."),
	;

	private final int status;
	private final String code;
	private final String message;

}
