package com.titi.titi_pusher.adapter.config;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.titi.titi_crypto_lib.util.CryptoUtils;

@Configuration
class CryptoUtilsConfig {

	@Bean
	@ConditionalOnMissingBean
	public CryptoUtils cryptoUtils(@Value(value = "${crypto.secret-key}") String secretKey) {
		return new CryptoUtils(secretKey.getBytes(StandardCharsets.UTF_8));
	}

}
