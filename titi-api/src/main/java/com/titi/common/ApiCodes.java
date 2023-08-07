package com.titi.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiCodes {

	@Getter
	@AllArgsConstructor
	public enum Result {

		;

		private final int status;
		private final String code;
		private final String message;

	}

	@Getter
	@AllArgsConstructor
	public enum Error {

		;

		private final int status;
		private final String code;
		private final String message;

	}

}
