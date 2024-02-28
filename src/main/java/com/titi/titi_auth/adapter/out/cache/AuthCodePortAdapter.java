package com.titi.titi_auth.adapter.out.cache;

import static com.titi.titi_common_lib.constant.Constants.*;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.titi.exception.TiTiErrorCodes;
import com.titi.exception.TiTiException;
import com.titi.infrastructure.cache.CacheManager;
import com.titi.titi_auth.application.port.out.PutAuthCodePort;
import com.titi.titi_auth.domain.AuthCode;
import com.titi.titi_common_lib.util.JacksonHelper;
import com.titi.titi_crypto_lib.util.HashingUtils;

@Slf4j
@Component
@RequiredArgsConstructor
class AuthCodePortAdapter implements PutAuthCodePort {

	private final CacheManager cacheManager;

	@Override
	public String put(AuthCode authCode) {
		final String key = this.getCacheKey(authCode);
		try {
			this.cacheManager.put(key, JacksonHelper.toJson(authCode), AuthCacheKeys.AUTH_CODE.getTimeToLive());
			log.info("[put] Success to put authCode in cache. key: {}.", key);
			return key;
		} catch (Exception e) {
			log.error("[put] Fail to put authCode in cache. key: {}.", key, e);
			throw new TiTiException(TiTiErrorCodes.GENERATE_AUTH_CODE_FAILURE);
		}
	}

	private String getCacheKey(AuthCode authCode) {
		return AuthCacheKeys.AUTH_CODE.getPrefix() + UNDERSCORE + HashingUtils.hashSha256(
			authCode.authType().getShortenName(), authCode.targetType().getShortenName(), authCode.targetValue()
		);
	}

}
