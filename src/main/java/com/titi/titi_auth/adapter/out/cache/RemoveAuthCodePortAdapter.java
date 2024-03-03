package com.titi.titi_auth.adapter.out.cache;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.titi.infrastructure.cache.CacheManager;
import com.titi.titi_auth.application.port.out.cache.RemoveAuthCodePort;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;
import com.titi.titi_auth.common.TiTiAuthException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemoveAuthCodePortAdapter implements RemoveAuthCodePort {

	private final CacheManager cacheManager;

	@Override
	public void invoke(Command command) {
		try {
			cacheManager.remove(command.authKey());
			log.info("Successfully removed the authCode from the cache. authKey: {}", command.authKey());
		} catch (Exception e) {
			log.error("Failed to remove the authCode from the cache. authKey: {} ", command.authKey(), e);
			throw new TiTiAuthException(TiTiAuthBusinessCodes.VERIFY_AUTH_CODE_FAILURE);
		}
	}

}
