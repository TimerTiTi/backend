package com.titi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.RequiredArgsConstructor;

import com.titi.security.SecurityConstants.AuthenticationWhiteList;
import com.titi.security.authentication.AccessDeniedHandlerImpl;
import com.titi.security.authentication.AuthenticationEntryPointImpl;
import com.titi.security.authentication.jwt.JwtAuthenticationFilter;
import com.titi.security.authentication.jwt.JwtAuthenticationProvider;
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
		http
			.cors()
			.and()

			.httpBasic().disable()
			.formLogin().disable()
			.csrf().disable()
			.logout().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()

			.exceptionHandling()
			.accessDeniedHandler(this.accessDeniedHandler)
			.and()

			.authorizeHttpRequests(auth ->
				auth.anyRequest().authenticated()
			)
			.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		final RequestMatcher matcher = new WhiteListRequestMatcher(AuthenticationWhiteList.getAll());
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
