package com.titi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.RequiredArgsConstructor;

import com.titi.security.authentication.exception.AccessDeniedHandlerImpl;
import com.titi.security.authentication.exception.AuthenticationEntryPointImpl;
import com.titi.security.authentication.jwt.JwtAuthenticationFilter;
import com.titi.security.authentication.jwt.JwtAuthenticationProvider;
import com.titi.security.constant.SecurityConstants;
import com.titi.security.matcher.WhiteListRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationEntryPointImpl authenticationEntryPoint;
	private final AccessDeniedHandlerImpl accessDeniedHandler;
	private final JwtAuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.cors(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(configurer -> configurer.accessDeniedHandler(this.accessDeniedHandler))
			.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
			.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		final RequestMatcher matcher = new WhiteListRequestMatcher(SecurityConstants.AuthenticationWhiteList.getAll());
		final JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher);
		filter.setAuthenticationFailureHandler(new AuthenticationEntryPointFailureHandler(this.authenticationEntryPoint));
		filter.setAuthenticationManager(new ProviderManager(this.authenticationProvider));
		return filter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
