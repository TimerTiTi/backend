package com.titi.auth.adapter.in.security.matcher;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;

import com.titi.auth.adapter.in.security.constant.SecurityConstants;

@ExtendWith(MockitoExtension.class)
class WhiteListRequestMatcherTest {

	private final WhiteListRequestMatcher whiteListRequestMatcher = new WhiteListRequestMatcher(SecurityConstants.AuthenticationWhiteList.getAll());

	@Test
	void testMatchesWhenRequestIsNotInWhiteListThenReturnTrue() {
		// given
		final String requestUriNotInWhiteList = "/test";
		final HttpServletRequest mockHttpServletRequest = getMockHttpServletRequest(requestUriNotInWhiteList);

		// when
		final boolean matches = whiteListRequestMatcher.matches(mockHttpServletRequest);

		// then
		assertThat(matches).isEqualTo(true);
	}

	@Test
	void testMatchesWhenRequestIsInWhiteListThenReturnFalse() {
		// given
		final String requestUriInWhiteList = SecurityConstants.AuthenticationWhiteList.SWAGGER_V3[0];
		final HttpServletRequest mockHttpServletRequest = getMockHttpServletRequest(requestUriInWhiteList);

		// when
		final boolean matches = whiteListRequestMatcher.matches(mockHttpServletRequest);

		// then
		assertThat(matches).isEqualTo(false);
	}

	private HttpServletRequest getMockHttpServletRequest(String requestUriInWhiteList) {
		final HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);
		when(mockHttpServletRequest.getServletPath()).thenReturn(requestUriInWhiteList);
		when(mockHttpServletRequest.getPathInfo()).thenReturn(null);
		return mockHttpServletRequest;
	}

}