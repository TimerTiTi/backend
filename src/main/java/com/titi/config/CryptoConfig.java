package com.titi.config;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.titi.crypto.util.CryptoUtils;

@Configuration
public class CryptoConfig {

	@Bean
	public CryptoUtils cryptoUtils(@Value(value = "${crypto.secret-key}") String secretKey) {
		return new CryptoUtils(secretKey.getBytes(StandardCharsets.UTF_8));
	}

}
