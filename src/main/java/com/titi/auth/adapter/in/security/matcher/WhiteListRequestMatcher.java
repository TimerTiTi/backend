package com.titi.auth.adapter.in.security.matcher;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.http.HttpServletRequest;
public class WhiteListRequestMatcher implements RequestMatcher {

	private final OrRequestMatcher matcher;

	public WhiteListRequestMatcher(List<String> whiteList) {
		final List<RequestMatcher> requestMatchers = whiteList.stream()
			.map(AntPathRequestMatcher::new)
			.collect(Collectors.toList());
		this.matcher = new OrRequestMatcher(requestMatchers);
	}

	/**
	 * Matches requests that are not in the WhiteList.
	 */
	@Override
	public boolean matches(HttpServletRequest request) {
		return !matcher.matches(request);
	}

}
