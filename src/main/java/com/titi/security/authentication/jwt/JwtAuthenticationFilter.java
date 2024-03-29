package com.titi.security.authentication.jwt;

import static com.titi.titi_common_lib.constant.Constants.*;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.titi.security.authentication.exception.TiTiAuthenticationException;
import com.titi.security.constant.SecurityConstants;
import com.titi.titi_common_lib.dto.ErrorResponse.FieldError;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	/**
	 * <a href="https://datatracker.ietf.org/doc/html/rfc6750">The OAuth 2.0 Authorization Framework: Bearer Token Usage</a>
	 */
	public static final String AUTHENTICATION_TYPE = "Bearer";
	private static final String AUTHENTICATION_HEADER_PREFIX = AUTHENTICATION_TYPE + SINGLE_SPACE;

	public JwtAuthenticationFilter(RequestMatcher matcher) {
		super(matcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		final String authorizationHeader = request.getHeader(SecurityConstants.REQUEST_HEADER_AUTHORIZATION);
		final String jwt = this.extractJwt(authorizationHeader);
		final Authentication authentication = JwtAuthenticationToken.init(jwt);
		return super.getAuthenticationManager().authenticate(authentication);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		super.getFailureHandler().onAuthenticationFailure(request, response, failed);
	}

	private String extractJwt(String authorizationHeader) {
		this.validateAuthorizationHeader(authorizationHeader);
		return authorizationHeader.substring(AUTHENTICATION_HEADER_PREFIX.length());
	}

	private void validateAuthorizationHeader(String authorizationHeader) {
		if (authorizationHeader == null) {
			final List<FieldError> errors = FieldError.of(SecurityConstants.REQUEST_HEADER_AUTHORIZATION, EMPTY, "Authorization header must not be null.");
			throw new TiTiAuthenticationException(errors);
		} else if (!authorizationHeader.startsWith(AUTHENTICATION_HEADER_PREFIX)) {
			final List<FieldError> errors = FieldError.of(SecurityConstants.REQUEST_HEADER_AUTHORIZATION, authorizationHeader, "Authorization header prefix is invalid.");
			throw new TiTiAuthenticationException(errors);
		}
	}

}
