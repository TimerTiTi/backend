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
import com.titi.titi_auth.application.port.out.cache.GetRefreshTokenPort;
import com.titi.titi_auth.common.TiTiAuthException;

@ExtendWith(MockitoExtension.class)
class GetRefreshTokenPortAdapterTest {

	@Mock
	private CacheManager cacheManager;

	@InjectMocks
	private GetRefreshTokenPortAdapter getRefreshTokenPortAdapter;

	@Test
	void successfulScenario() throws Exception {
		// given
		given(cacheManager.get(anyString())).willReturn(Optional.of("refreshToken"));

		// when
		final GetRefreshTokenPort.Command command = GetRefreshTokenPort.Command.builder()
			.subject("subject")
			.build();
		final GetRefreshTokenPort.Result result = getRefreshTokenPortAdapter.invoke(command);

		// then
		assertThat(result).isNotNull();
		assertThat(result.refreshToken().isPresent()).isTrue();
		verify(cacheManager, times(1)).get(anyString());
	}

	@Test
	void successfulButEmptyScenario() throws Exception {
		// given
		given(cacheManager.get(anyString())).willReturn(Optional.empty());

		// when
		final GetRefreshTokenPort.Command command = GetRefreshTokenPort.Command.builder()
			.subject("subject")
			.build();
		final GetRefreshTokenPort.Result result = getRefreshTokenPortAdapter.invoke(command);

		// then
		assertThat(result).isNotNull();
		assertThat(result.refreshToken().isEmpty()).isTrue();
		verify(cacheManager, times(1)).get(anyString());
	}

	@Test
	void cacheServerErrorScenario() throws Exception {
		// given
		given(cacheManager.get(anyString())).willThrow(Exception.class);

		// when
		final GetRefreshTokenPort.Command command = GetRefreshTokenPort.Command.builder()
			.subject("subject")
			.build();
		final ThrowableAssert.ThrowingCallable throwingCallable = () -> getRefreshTokenPortAdapter.invoke(command);

		// then
		assertThatCode(throwingCallable).isInstanceOf(TiTiAuthException.class);
		verify(cacheManager, times(1)).get(anyString());
	}

}