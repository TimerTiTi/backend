package com.titi.titi_auth.adapter.out.cache;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.exception.TiTiException;
import com.titi.infrastructure.cache.CacheManager;
import com.titi.titi_auth.application.port.out.cache.PutAuthCodePort;

@ExtendWith(MockitoExtension.class)
class AuthCodePortAdapterTest {

	@Mock
	private CacheManager cacheManager;

	@InjectMocks
	private AuthCodePortAdapter authCodePortAdapter;

	@Test
	void putTestSuccess() throws Exception {
		// given
		final String authKey = "authKey";
		final String authCode = "authCode";

		// when
		authCodePortAdapter.invoke(
			PutAuthCodePort.Command.builder()
				.authKey(authKey)
				.authCode(authCode)
				.build()
		);

		// then
		verify(cacheManager, times(1)).put(anyString(), anyString(), anyLong());
	}

	@Test
	void putTestThrowsRuntimeException() throws Exception {
		// given
		final String authKey = "authKey";
		final String authCode = "authCode";

		doThrow(Exception.class).when(cacheManager).put(anyString(), anyString(), anyLong());

		// when-then
		assertThatCode(() -> authCodePortAdapter.invoke(
			PutAuthCodePort.Command.builder()
				.authKey(authKey)
				.authCode(authCode)
				.build()
		)).isInstanceOf(TiTiException.class);
	}

}
