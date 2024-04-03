package com.titi.titi_auth.adapter.out.cache;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.titi.infrastructure.cache.CacheManager;
import com.titi.titi_auth.application.port.out.cache.PutRefreshTokenPort;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;
import com.titi.titi_auth.common.TiTiAuthException;

@Slf4j
@Component
@RequiredArgsConstructor
public class PutRefreshTokenPortAdapter implements PutRefreshTokenPort {

	private final CacheManager cacheManager;

	@Override
	public void invoke(Command command) {
		final String key = command.generateCacheKey();
		try {
			this.cacheManager.put(key, command.refreshToken(), AuthCacheKeys.REFRESH_TOKEN.getTimeToLive());
			log.info("Successfully put refreshToken in cache. key: {}.", key);
		} catch (Exception e) {
			log.error("Failed to put refreshToken in cache. key: {}.", key, e);
			throw new TiTiAuthException(TiTiAuthBusinessCodes.CACHE_SERVER_ERROR);
		}
	}

}
