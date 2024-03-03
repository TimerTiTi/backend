package com.titi.titi_auth.adapter.in.web.api;

import static org.mockito.ArgumentMatchers.*;
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

import com.titi.titi_auth.application.port.in.VerifyAuthCodeUseCase;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;
import com.titi.titi_auth.common.TiTiAuthException;
import com.titi.titi_common_lib.util.JacksonHelper;

@WebMvcTest(controllers = VerifyAuthCodeController.class)
class VerifyAuthCodeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VerifyAuthCodeUseCase verifyAuthCodeUseCase;

	private static VerifyAuthCodeController.VerifyAuthCodeRequestBody getVerifyAuthCodeRequestBody() {
		return VerifyAuthCodeController.VerifyAuthCodeRequestBody.builder()
			.authKey("authKey")
			.authCode("authCode")
			.build();
	}

	private ResultActions mockVerifyAuthCode(VerifyAuthCodeController.VerifyAuthCodeRequestBody requestBody) throws Exception {
		return mockMvc.perform(post("/api/auth/code/verify")
			.content(JacksonHelper.toJson(requestBody))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.with(csrf()));
	}

	@Test
	@WithMockUser
	void whenSuccessToVerifyAuthCodeThenCodeIsAU1001() throws Exception {
		// given
		given(verifyAuthCodeUseCase.invoke(any())).willReturn(VerifyAuthCodeUseCase.Result.builder().authToken("authToken").build());
		final VerifyAuthCodeController.VerifyAuthCodeRequestBody requestBody = getVerifyAuthCodeRequestBody();

		// when
		final ResultActions perform = mockVerifyAuthCode(requestBody);

		// then
		perform.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(TiTiAuthBusinessCodes.VERIFY_AUTH_CODE_SUCCESS.getCode()))
			.andExpect(jsonPath("$.message").value(TiTiAuthBusinessCodes.VERIFY_AUTH_CODE_SUCCESS.getMessage()))
			.andExpect(jsonPath("$.auth_token").exists());
	}

	@Test
	@WithMockUser
	void whenAuthCodeIsNotInCacheThenCodeIsAU1002() throws Exception {
		// given
		final VerifyAuthCodeController.VerifyAuthCodeRequestBody requestBody = getVerifyAuthCodeRequestBody();
		doThrow(new TiTiAuthException(TiTiAuthBusinessCodes.INVALID_AUTH_CODE)).when(verifyAuthCodeUseCase).invoke(any());

		// when
		final ResultActions perform = mockVerifyAuthCode(requestBody);

		// then
		perform.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(TiTiAuthBusinessCodes.INVALID_AUTH_CODE.getCode()))
			.andExpect(jsonPath("$.message").value(TiTiAuthBusinessCodes.INVALID_AUTH_CODE.getMessage()));
	}

	@Test
	@WithMockUser
	void whenAuthCodeIsNotMatchedThenCodeIsAU1003() throws Exception {
		// given
		final VerifyAuthCodeController.VerifyAuthCodeRequestBody requestBody = getVerifyAuthCodeRequestBody();
		doThrow(new TiTiAuthException(TiTiAuthBusinessCodes.MISMATCHED_AUTH_CODE)).when(verifyAuthCodeUseCase).invoke(any());

		// when
		final ResultActions perform = mockVerifyAuthCode(requestBody);

		// then
		perform.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(TiTiAuthBusinessCodes.MISMATCHED_AUTH_CODE.getCode()))
			.andExpect(jsonPath("$.message").value(TiTiAuthBusinessCodes.MISMATCHED_AUTH_CODE.getMessage()));
	}

	@Test
	@WithMockUser
	void whenThrowsInternalServerErrorThenCodeIsAU7001() throws Exception {
		// given
		final VerifyAuthCodeController.VerifyAuthCodeRequestBody requestBody = getVerifyAuthCodeRequestBody();
		doThrow(new TiTiAuthException(TiTiAuthBusinessCodes.VERIFY_AUTH_CODE_FAILURE)).when(verifyAuthCodeUseCase).invoke(any());

		// when
		final ResultActions perform = mockVerifyAuthCode(requestBody);

		// then
		perform.andExpect(status().isInternalServerError())
			.andExpect(jsonPath("$.code").value(TiTiAuthBusinessCodes.VERIFY_AUTH_CODE_FAILURE.getCode()))
			.andExpect(jsonPath("$.message").value(TiTiAuthBusinessCodes.VERIFY_AUTH_CODE_FAILURE.getMessage()));
	}

}
