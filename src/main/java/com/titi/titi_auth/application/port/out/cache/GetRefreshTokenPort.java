package com.titi.titi_auth.application.port.out.cache;

import java.util.Optional;

import lombok.Builder;

import com.titi.titi_auth.adapter.out.cache.AuthCacheKeys;

public interface GetRefreshTokenPort {

	Result invoke(Command command);

	@Builder
	record Command(
		String subject
	) {

		public String generateCacheKey() {
			return AuthCacheKeys.REFRESH_TOKEN.getPrefix() + this.subject;
		}

	}

	@Builder
	record Result(
		Optional<String> refreshToken
	) {

	}

}
