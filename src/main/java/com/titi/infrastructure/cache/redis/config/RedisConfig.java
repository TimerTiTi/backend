package com.titi.infrastructure.cache.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(this.host, this.port);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		this.setKeyValueSerializer(redisTemplate);
		this.setHashKeyValueSerializer(redisTemplate);
		this.setDefaultSerializer(redisTemplate);
		return redisTemplate;
	}

	private void setDefaultSerializer(RedisTemplate<String, Object> redisTemplate) {
		redisTemplate.setDefaultSerializer(new StringRedisSerializer());
	}

	private void setHashKeyValueSerializer(RedisTemplate<String, Object> redisTemplate) {
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new StringRedisSerializer());
	}

	private void setKeyValueSerializer(RedisTemplate<String, Object> redisTemplate) {
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
	}

}
