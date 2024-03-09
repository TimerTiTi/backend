package com.titi.titi_user.adapter.in.web.api;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.titi.titi_user.application.port.in.CheckUsernameUseCase;
import com.titi.titi_user.common.TiTiUserBusinessCodes;

@WebMvcTest(controllers = CheckUsernameController.class)
class CheckUsernameControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CheckUsernameUseCase checkUsernameUseCase;

	private static Stream<Arguments> whenSuccessToCheckUsername() {
		return Stream.of(
			Arguments.of(false, TiTiUserBusinessCodes.DOES_NOT_EXIST_USERNAME),
			Arguments.of(true, TiTiUserBusinessCodes.ALREADY_EXISTS_USERNAME)
		);
	}

	private ResultActions mockCheckUsername(String username) throws Exception {
		return mockMvc.perform(get("/api/user/members/check")
			.queryParam("username", username)
			.accept(MediaType.APPLICATION_JSON)
			.with(csrf()));
	}

	@ParameterizedTest
	@MethodSource
	@WithMockUser
	void whenSuccessToCheckUsername(boolean isPresent, TiTiUserBusinessCodes businessCodes) throws Exception {
		// given
		given(checkUsernameUseCase.invoke(any())).willReturn(CheckUsernameUseCase.Result.builder().isPresent(isPresent).build());
		final String username = "test@gmail.com";

		// when
		final ResultActions perform = mockCheckUsername(username);

		// then
		perform.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(businessCodes.getCode()))
			.andExpect(jsonPath("$.message").value(businessCodes.getMessage()))
			.andExpect(jsonPath("$.is_present").value(isPresent));
	}

}