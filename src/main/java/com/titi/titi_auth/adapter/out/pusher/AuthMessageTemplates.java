package com.titi.titi_auth.adapter.out.pusher;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthMessageTemplates {
	// TODO: Multilingual Support for Korean, English, Chinese.
	GENERATE_AUTH_CODE("[Timer TiTi] 인증코드를 발송드려요.", "인증코드는 %s 이에요.");

	private final String subject;
	private final String text;
}
