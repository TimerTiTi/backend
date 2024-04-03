package com.titi.titi_auth.adapter.out.cache;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.infrastructure.cache.CacheManager;
import com.titi.titi_auth.application.port.out.cache.PutRefreshTokenPort;
import com.titi.titi_auth.common.TiTiAuthException;

@ExtendWith(MockitoExtension.class)
class PutRefreshTokenPortAdapterTest {

	@Mock
	private CacheManager cacheManager;

	@InjectMocks
	private PutRefreshTokenPortAdapter putRefreshTokenPortAdapter;

	@Test
	void successfulScenario() throws Exception {
		// when
		final PutRefreshTokenPort.Command command = PutRefreshTokenPort.Command.builder()
			.subject("subject")
			.refreshToken("refreshToken")
			.build();
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> putRefreshTokenPortAdapter.invoke(command);

		// then
		assertThatCode(throwingCallable).doesNotThrowAnyException();
		verify(cacheManager, times(1)).put(anyString(), anyString(), eq(AuthCacheKeys.REFRESH_TOKEN.getTimeToLive()));
	}

	@Test
	void cacheServerErrorScenario() throws Exception {
		// given
		doThrow(Exception.class).when(cacheManager).put(anyString(), anyString(), eq(AuthCacheKeys.REFRESH_TOKEN.getTimeToLive()));

		// when
		final PutRefreshTokenPort.Command command = PutRefreshTokenPort.Command.builder()
			.subject("subject")
			.refreshToken("refreshToken")
			.build();
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> putRefreshTokenPortAdapter.invoke(command);

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiAuthException.class);
		verify(cacheManager, times(1)).put(anyString(), anyString(), eq(AuthCacheKeys.REFRESH_TOKEN.getTimeToLive()));
	}

}