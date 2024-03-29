package com.titi.security.authentication.exception;

import java.io.OutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import com.titi.exception.TiTiErrorCodes;
import com.titi.titi_common_lib.dto.ErrorResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
		final TiTiAuthenticationException exception = (TiTiAuthenticationException)authException;
		final ErrorResponse errorResponse = ErrorResponse.of(exception.getErrorCode(), exception.getErrors());

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		try (OutputStream os = response.getOutputStream()) {
			objectMapper.writeValue(os, errorResponse);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
