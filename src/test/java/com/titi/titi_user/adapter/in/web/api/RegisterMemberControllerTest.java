package com.titi.titi_user.adapter.in.web.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.titi.titi_common_lib.util.JacksonHelper;
import com.titi.titi_user.adapter.in.web.api.RegisterMemberController.RegisterMemberRequestBody;
import com.titi.titi_user.application.port.in.RegisterMemberUseCase;
import com.titi.titi_user.common.TiTiUserBusinessCodes;

@WebMvcTest(controllers = RegisterMemberController.class)
class RegisterMemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RegisterMemberUseCase registerMemberUseCase;

	private static RegisterMemberRequestBody getRegisterMemberRequestBody() {
		return RegisterMemberRequestBody.builder()
			.username("test@gmail.com")
			.encodedEncryptedPassword("encodedEncryptedPassword")
			.nickname("nickname")
			.authToken("authToken")
			.build();
	}

	private ResultActions mockRegisterMember(RegisterMemberRequestBody requestBody) throws Exception {
		return mockMvc.perform(post("/api/user/members/register")
			.content(JacksonHelper.toJson(requestBody))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.with(csrf()));
	}

	@Test
	@WithMockUser
	void whenSuccessToRegisterMemberThenCodeIsUSR1002() throws Exception {
		// given
		final RegisterMemberRequestBody requestBody = getRegisterMemberRequestBody();

		// when
		final ResultActions perform = mockRegisterMember(requestBody);

		// then
		perform.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(TiTiUserBusinessCodes.REGISTER_MEMBER_SUCCESS.getCode()))
			.andExpect(jsonPath("$.message").value(TiTiUserBusinessCodes.REGISTER_MEMBER_SUCCESS.getMessage()));
	}

}