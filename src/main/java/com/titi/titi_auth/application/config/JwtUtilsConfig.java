package com.titi.titi_auth.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.titi.titi_common_lib.util.JwtUtils;

@Configuration
class JwtUtilsConfig {

	@Bean
	@ConditionalOnMissingBean
	public JwtUtils jwtUtils(@Value("${jwt.secret-key}") String secretKey) {
		return new JwtUtils(secretKey);
	}

}
