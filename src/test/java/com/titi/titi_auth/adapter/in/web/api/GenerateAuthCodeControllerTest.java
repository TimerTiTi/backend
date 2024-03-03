package com.titi.titi_auth.adapter.in.web.api;

import static org.mockito.BDDMockito.*;
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

import com.titi.titi_auth.adapter.in.web.api.GenerateAuthCodeController.GenerateAuthCodeRequestBody;
import com.titi.titi_auth.application.port.in.GenerateAuthCodeUseCase;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;
import com.titi.titi_auth.common.TiTiAuthException;
import com.titi.titi_auth.domain.AuthenticationType;
import com.titi.titi_auth.domain.TargetType;
import com.titi.titi_common_lib.util.JacksonHelper;

@WebMvcTest(controllers = GenerateAuthCodeController.class)
class GenerateAuthCodeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GenerateAuthCodeUseCase generateAuthCodeUseCase;

	private static GenerateAuthCodeRequestBody getGenerateAuthCodeRequestBody() {
		return GenerateAuthCodeRequestBody.builder()
			.authType(AuthenticationType.SIGN_UP)
			.targetType(TargetType.EMAIL)
			.targetValue("test@example.com")
			.build();
	}

	private ResultActions mockGenerateAuthCode(GenerateAuthCodeRequestBody requestBody) throws Exception {
		return mockMvc.perform(post("/api/auth/code")
			.content(JacksonHelper.toJson(requestBody))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.with(csrf()));
	}

	@Test
	@WithMockUser
	void whenSuccessToGenerateAuthCodeThenCodeIsAU1000() throws Exception {
		// given
		given(generateAuthCodeUseCase.invoke(any())).willReturn(GenerateAuthCodeUseCase.Result.builder().authKey("authKey").build());
		final GenerateAuthCodeRequestBody requestBody = getGenerateAuthCodeRequestBody();

		// when
		final ResultActions perform = mockGenerateAuthCode(requestBody);

		// then
		perform.andExpect(status().isCreated())
			.andExpect(jsonPath("$.code").value(TiTiAuthBusinessCodes.GENERATE_AUTH_CODE_SUCCESS.getCode()))
			.andExpect(jsonPath("$.message").value(TiTiAuthBusinessCodes.GENERATE_AUTH_CODE_SUCCESS.getMessage()))
			.andExpect(jsonPath("$.auth_key").exists());
	}

	@Test
	@WithMockUser
	void whenThrowsInternalServerErrorThenCodeIsAU7000() throws Exception {
		// given
		final GenerateAuthCodeRequestBody requestBody = getGenerateAuthCodeRequestBody();
		doThrow(new TiTiAuthException(TiTiAuthBusinessCodes.GENERATE_AUTH_CODE_FAILURE)).when(generateAuthCodeUseCase).invoke(any());

		// when
		final ResultActions perform = mockGenerateAuthCode(requestBody);

		// then
		perform.andExpect(status().isInternalServerError())
			.andExpect(jsonPath("$.code").value(TiTiAuthBusinessCodes.GENERATE_AUTH_CODE_FAILURE.getCode()))
			.andExpect(jsonPath("$.message").value(TiTiAuthBusinessCodes.GENERATE_AUTH_CODE_FAILURE.getMessage()));
	}

}
