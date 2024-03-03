package com.titi.titi_auth.adapter.out.cache;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.titi.infrastructure.cache.CacheManager;
import com.titi.titi_auth.application.port.out.cache.GetAuthCodePort;
import com.titi.titi_auth.common.TiTiAuthException;

@ExtendWith(MockitoExtension.class)
class GetAuthCodePortAdapterTest {

	@Mock
	private CacheManager cacheManager;

	@InjectMocks
	private GetAuthCodePortAdapter getAuthCodePortAdapter;

	@Test
	void whenAuthCodeIsInCacheThenReturnAuthCode() throws Exception {
		// given
		final String authKey = "authKey";
		final String authCode = "authCode";
		given(cacheManager.get(authKey)).willReturn(Optional.of(authCode));

		// when
		final GetAuthCodePort.Result result = getAuthCodePortAdapter.invoke(GetAuthCodePortAdapter.Command.builder().authKey(authKey).build());

		// then
		assertThat(result).isNotNull();
		assertThat(result.authCode()).isNotNull();
		assertThat(result.authCode().isPresent()).isTrue();
		verify(cacheManager, times(1)).get(authKey);
	}

	@Test
	void whenAuthCodeIsNotInCacheThenReturnEmpty() throws Exception {
		// given
		final String authKey = "authKey";
		given(cacheManager.get(authKey)).willReturn(Optional.empty());

		// when
		final GetAuthCodePort.Result result = getAuthCodePortAdapter.invoke(GetAuthCodePortAdapter.Command.builder().authKey(authKey).build());

		// then
		assertThat(result).isNotNull();
		assertThat(result.authCode()).isNotNull();
		assertThat(result.authCode().isEmpty()).isTrue();
		verify(cacheManager, times(1)).get(authKey);
	}

	@Test
	void whenCacheMangerThrowsExceptionThenThrowsTiTiAuthException() throws Exception {
		// given
		final String authKey = "authKey";
		given(cacheManager.get(authKey)).willThrow(Exception.class);

		// when
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> getAuthCodePortAdapter.invoke(GetAuthCodePortAdapter.Command.builder().authKey(authKey).build());

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiAuthException.class);
		verify(cacheManager, times(1)).get(authKey);
	}

}
