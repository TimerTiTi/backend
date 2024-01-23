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
import com.titi.titi_auth.domain.AuthCode;
import com.titi.titi_auth.domain.AuthenticationType;
import com.titi.titi_auth.domain.TargetType;

@ExtendWith(MockitoExtension.class)
class AuthCodePortAdapterTest {

	@Mock
	private CacheManager cacheManager;

	@InjectMocks
	private AuthCodePortAdapter authCodePortAdapter;

	@Test
	void putTestSuccess() throws Exception {
		// given
		final AuthCode authCode = new AuthCode(AuthenticationType.SIGN_UP, "123", TargetType.EMAIL, "test@example.com");

		// when
		authCodePortAdapter.put(authCode);

		// then
		verify(cacheManager, times(1)).put(anyString(), anyString(), anyLong());
	}

	@Test
	void putTestThrowsRuntimeException() throws Exception {
		// given
		final AuthCode authCode = new AuthCode(AuthenticationType.SIGN_UP, "123", TargetType.EMAIL, "test@example.com");

		doThrow(Exception.class).when(cacheManager).put(anyString(), anyString(), anyLong());

		// when-then
		assertThatCode(() -> authCodePortAdapter.put(authCode)).isInstanceOf(TiTiException.class);
	}

}
