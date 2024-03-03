package com.titi.titi_auth.adapter.out.cache;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.titi.exception.TiTiErrorCodes;
import com.titi.exception.TiTiException;
import com.titi.infrastructure.cache.CacheManager;
import com.titi.titi_auth.application.port.out.GetAuthCodePort;
import com.titi.titi_auth.domain.AuthCode;
import com.titi.titi_common_lib.util.JacksonHelper;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetAuthCodePortAdapter implements GetAuthCodePort {

	private final CacheManager cacheManager;

	@Override
	public Result invoke(Command command) {
		try {
			final Optional<String> optionalAuthCode = this.cacheManager.get(command.authKey());
			if (optionalAuthCode.isPresent()) {
				log.info("Successfully got the authCode from the cache. authKey: {} authCode: {}", command.authKey(), optionalAuthCode.get());
				return Result.builder()
					.authCode(Optional.of(JacksonHelper.fromJson(optionalAuthCode.get(), AuthCode.class)))
					.build();
			} else {
				log.info("Failed to get the authCode from the cache. This may be due to the expiration of the authCode or the invalidity of the authKey. authKey: {}", command.authKey());
				return Result.builder().authCode(Optional.empty()).build();
			}
		} catch (Exception e) {
			log.error("Failed to get the authCode from the cache. authKey: {} ", command.authKey(), e);
			throw new TiTiException(TiTiErrorCodes.VERIFY_AUTH_CODE_FAILURE);
		}
	}

}
