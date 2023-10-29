package com.titi.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiCodes {

	@Getter
	@AllArgsConstructor
	public enum ResultCode {

		;

		private final int status;
		private final String code;
		private final String message;

	}

	@Getter
	@AllArgsConstructor
	public enum ErrorCode {

		INTERNAL_SERVER_ERROR(500, "EG001", "내부 서버 오류입니다."),
		INPUT_VALUE_INVALID(400, "EG002", "입력 값이 유효하지 않습니다."),
		INPUT_TYPE_INVALID(400, "EG003", "입력 타입이 유효하지 않습니다."),
		METHOD_NOT_ALLOWED(405, "EG004", "허용되지 않은 HTTP method 입니다."),
		HTTP_MESSAGE_NOT_READABLE(400, "EG005", "HTTP Request Body 형식이 올바르지 않습니다."),
		REQUEST_PARAMETER_MISSING(400, "EG006", "요청 파라미터는 필수입니다."),
		REQUEST_HEADER_MISSING(400, "EG007", "요청 헤더는 필수입니다."),
		;

		private final int status;
		private final String code;
		private final String message;

	}

}
