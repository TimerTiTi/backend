package com.titi.titi_auth.application.port.out.cache;

import lombok.Builder;

import com.titi.titi_auth.adapter.out.cache.AuthCacheKeys;

public interface PutRefreshTokenPort {

	void invoke(Command command);

	@Builder
	record Command(
		String subject,
		String refreshToken
	) {

		public String generateCacheKey() {
			return AuthCacheKeys.REFRESH_TOKEN.getPrefix() + this.subject;
		}

	}

}
