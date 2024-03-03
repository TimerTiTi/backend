package com.titi.infrastructure.cache.redis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.titi.infrastructure.cache.CacheManager;

@Component
@RequiredArgsConstructor
class RedisAdapter implements CacheManager {

	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public void put(String key, String value, long timeToLive) throws Exception {
		this.redisTemplate.opsForValue().set(key, value, timeToLive, TimeUnit.SECONDS);
	}

	@Override
	public Optional<String> get(String key) throws Exception {
		return Optional.ofNullable(this.redisTemplate.opsForValue().get(key));
	}

	@Override
	public void remove(String key) throws Exception {
		this.redisTemplate.delete(key);
	}

}
