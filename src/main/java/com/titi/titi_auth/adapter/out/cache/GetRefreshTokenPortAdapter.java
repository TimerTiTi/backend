package com.titi.titi_auth.adapter.out.cache;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.titi.infrastructure.cache.CacheManager;
import com.titi.titi_auth.application.port.out.cache.GetRefreshTokenPort;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;
import com.titi.titi_auth.common.TiTiAuthException;

@Slf4j
@Component
@RequiredArgsConstructor
class GetRefreshTokenPortAdapter implements GetRefreshTokenPort {

	private final CacheManager cacheManager;

	@Override
	public Result invoke(Command command) {
		final String key = command.generateCacheKey();
		try {
			final Optional<String> refreshTokenOptional = this.cacheManager.get(key);
			refreshTokenOptional.ifPresentOrElse(
				refreshToken -> log.info("Successfully got the refreshToken from the cache. key: {} refreshToken: {}", key, refreshToken),
				() -> log.info("Failed to get the refreshToken from the cache. This may be due to the expiration of the refreshToken or the invalidity of the key. key: {}", key)
			);
			return GetRefreshTokenPort.Result.builder().refreshToken(refreshTokenOptional).build();
		} catch (Exception e) {
			log.error("Failed to get the refreshToken from the cache. key: {} ", key, e);
			throw new TiTiAuthException(TiTiAuthBusinessCodes.CACHE_SERVER_ERROR);
		}
	}

}
