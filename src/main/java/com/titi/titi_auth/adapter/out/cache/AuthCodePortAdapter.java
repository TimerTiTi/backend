package com.titi.titi_auth.adapter.out.cache;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.titi.infrastructure.cache.CacheManager;
import com.titi.titi_auth.application.port.out.cache.PutAuthCodePort;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;
import com.titi.titi_auth.common.TiTiAuthException;

@Slf4j
@Component
@RequiredArgsConstructor
class AuthCodePortAdapter implements PutAuthCodePort {

	private final CacheManager cacheManager;

	@Override
	public void invoke(Command command) {
		try {
			this.cacheManager.put(command.authKey(), command.authCode(), AuthCacheKeys.AUTH_CODE.getTimeToLive());
			log.info("Successfully put authCode in cache. authKey: {}.", command.authKey());
		} catch (Exception e) {
			log.error("Failed to put authCode in cache. authKey: {}.", command.authKey(), e);
			throw new TiTiAuthException(TiTiAuthBusinessCodes.GENERATE_AUTH_CODE_FAILURE);
		}
	}

}
