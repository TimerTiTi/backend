package com.titi.infrastructure.cache.redis;

import static com.titi.titi_common_lib.constant.Constants.*;
import static org.mockito.BDDMockito.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@ExtendWith(MockitoExtension.class)
class RedisAdapterTest {

	@Mock
	private RedisTemplate<String, String> redisTemplate;

	@Mock
	private ValueOperations<String, String> valueOperations;

	@InjectMocks
	private RedisAdapter redisAdapter;

	@Test
	void putTest() {
		// given
		final String key = "testKey";
		final String value = "testValue";
		long timeToLive = 60L * SECONDS;

		given(redisTemplate.opsForValue()).willReturn(valueOperations);

		// when
		redisAdapter.put(key, value, timeToLive);

		// then
		verify(redisTemplate.opsForValue()).set(eq(key), eq(value), eq(timeToLive), eq(TimeUnit.SECONDS));
	}

}
