package com.titi.titi_user.adapter.in.web.api;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.titi.titi_common_lib.util.JacksonHelper;
import com.titi.titi_user.adapter.in.web.api.LoginController.LoginRequestBody;
import com.titi.titi_user.application.port.in.LoginUseCase;
import com.titi.titi_user.common.TiTiUserBusinessCodes;
import com.titi.titi_user.common.TiTiUserException;

@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LoginUseCase loginUseCase;

	private static LoginRequestBody getLoginRequestBody() {
		return LoginRequestBody.builder()
			.username("test@gmail.com")
			.encodedEncryptedPassword("encodedEncryptedPassword")
			.deviceId(UUID.randomUUID().toString())
			.build();
	}

	private ResultActions mockRegisterMember(LoginRequestBody requestBody) throws Exception {
		return mockMvc.perform(post("/api/user/members/login")
			.content(JacksonHelper.toJson(requestBody))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.with(csrf()));
	}

	@Test
	@WithMockUser
	void whenSuccessToLoginThenCodeIsUSR1005() throws Exception {
		// given
		given(loginUseCase.invoke(any(LoginUseCase.Command.class))).willReturn(
			LoginUseCase.Result.builder()
				.accessToken("accessToken")
				.refreshToken("refreshToken")
				.build()
		);

		// when
		final ResultActions perform = mockRegisterMember(getLoginRequestBody());

		// then
		perform.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(TiTiUserBusinessCodes.LOGIN_SUCCESS.getCode()))
			.andExpect(jsonPath("$.message").value(TiTiUserBusinessCodes.LOGIN_SUCCESS.getMessage()))
			.andExpect(jsonPath("$.access_token").isNotEmpty())
			.andExpect(jsonPath("$.refresh_token").isNotEmpty());
		verify(loginUseCase, times(1)).invoke(any(LoginUseCase.Command.class));
	}

	@Test
	@WithMockUser
	void whenFailureToLoginThenCodeIsUSR1006() throws Exception {
		// given
		given(loginUseCase.invoke(any(LoginUseCase.Command.class))).willThrow(new TiTiUserException(TiTiUserBusinessCodes.LOGIN_FAILURE_MISMATCHED_MEMBER_INFORMATION));

		// when
		final ResultActions perform = mockRegisterMember(getLoginRequestBody());

		// then
		perform.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(TiTiUserBusinessCodes.LOGIN_FAILURE_MISMATCHED_MEMBER_INFORMATION.getCode()))
			.andExpect(jsonPath("$.message").value(TiTiUserBusinessCodes.LOGIN_FAILURE_MISMATCHED_MEMBER_INFORMATION.getMessage()));
		verify(loginUseCase, times(1)).invoke(any(LoginUseCase.Command.class));
	}

}