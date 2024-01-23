package com.titi.titi_auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthenticationType {
	SIGN_UP("SU"),
	FIND_ID("FI"),
	FIND_PW("FP"),
	CHANGE_ID("CI"),
	CHANGE_PW("CP");

	private final String shortenName;
}
