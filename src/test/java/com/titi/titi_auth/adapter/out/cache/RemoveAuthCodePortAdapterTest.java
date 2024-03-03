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
import com.titi.titi_auth.application.port.out.cache.RemoveAuthCodePort;
import com.titi.titi_auth.common.TiTiAuthException;

@ExtendWith(MockitoExtension.class)
class RemoveAuthCodePortAdapterTest {

	@Mock
	private CacheManager cacheManager;

	@InjectMocks
	private RemoveAuthCodePortAdapter removeAuthCodePortAdapter;

	@Test
	void successfulScenario() throws Exception {
		// given
		final String authKey = "authKey";

		// when
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> removeAuthCodePortAdapter.invoke(RemoveAuthCodePort.Command.builder().authKey(authKey).build());

		// then
		assertThatCode(throwingCallable).doesNotThrowAnyException();
		verify(cacheManager, times(1)).remove(authKey);
	}

	@Test
	void whenCacheMangerThrowsExceptionThenThrowsTiTiAuthException() throws Exception {
		// given
		final String authKey = "authKey";
		doThrow(Exception.class).when(cacheManager).remove(authKey);

		// when
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> removeAuthCodePortAdapter.invoke(RemoveAuthCodePort.Command.builder().authKey(authKey).build());

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiAuthException.class);
		verify(cacheManager, times(1)).remove(authKey);
	}

}
