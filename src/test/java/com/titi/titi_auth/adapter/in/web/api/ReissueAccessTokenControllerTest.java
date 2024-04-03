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

import com.titi.titi_auth.application.port.in.ReissueAccessTokenUseCase;
import com.titi.titi_auth.common.TiTiAuthBusinessCodes;
import com.titi.titi_auth.common.TiTiAuthException;
import com.titi.titi_common_lib.util.JacksonHelper;

@WebMvcTest(controllers = ReissueAccessTokenController.class)
class ReissueAccessTokenControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReissueAccessTokenUseCase reissueAccessTokenUseCase;

	private ResultActions mockReissueAccessToken(ReissueAccessTokenController.ReissueAccessTokenRequestBody requestBody) throws Exception {
		return mockMvc.perform(post("/api/auth/token/reissue")
			.content(JacksonHelper.toJson(requestBody))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.with(csrf()));
	}

	private ReissueAccessTokenController.ReissueAccessTokenRequestBody getReissueAccessTokenRequestBody() {
		return ReissueAccessTokenController.ReissueAccessTokenRequestBody.builder()
			.refreshToken("refreshToken")
			.build();
	}

	@Test
	@WithMockUser
	void whenSuccessToReissueAccessTokenThenCodeIsAU1004() throws Exception {
		// given
		final ReissueAccessTokenUseCase.Result result = ReissueAccessTokenUseCase.Result.builder()
			.accessToken("accessToken")
			.refreshToken("refreshToken")
			.build();
		given(reissueAccessTokenUseCase.invoke(any(ReissueAccessTokenUseCase.Command.class))).willReturn(result);
		final ReissueAccessTokenController.ReissueAccessTokenRequestBody requestBody = getReissueAccessTokenRequestBody();

		// when
		final ResultActions perform = mockReissueAccessToken(requestBody);

		// then
		perform.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(TiTiAuthBusinessCodes.REISSUE_ACCESS_TOKEN_SUCCESS.getCode()))
			.andExpect(jsonPath("$.message").value(TiTiAuthBusinessCodes.REISSUE_ACCESS_TOKEN_SUCCESS.getMessage()))
			.andExpect(jsonPath("$.access_token").isNotEmpty())
			.andExpect(jsonPath("$.refresh_token").isNotEmpty());
		verify(reissueAccessTokenUseCase, times(1)).invoke(any(ReissueAccessTokenUseCase.Command.class));
	}

	@Test
	@WithMockUser
	void whenFailureToReissueAccessTokenThenCodeIsAU1005() throws Exception {
		// given
		final ReissueAccessTokenUseCase.Result result = ReissueAccessTokenUseCase.Result.builder()
			.accessToken("accessToken")
			.refreshToken("refreshToken")
			.build();
		given(reissueAccessTokenUseCase.invoke(any(ReissueAccessTokenUseCase.Command.class)))
			.willThrow(new TiTiAuthException(TiTiAuthBusinessCodes.REISSUE_ACCESS_TOKEN_FAILURE_INVALID_REFRESH_TOKEN));
		final ReissueAccessTokenController.ReissueAccessTokenRequestBody requestBody = getReissueAccessTokenRequestBody();

		// when
		final ResultActions perform = mockReissueAccessToken(requestBody);

		// then
		perform.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(TiTiAuthBusinessCodes.REISSUE_ACCESS_TOKEN_FAILURE_INVALID_REFRESH_TOKEN.getCode()))
			.andExpect(jsonPath("$.message").value(TiTiAuthBusinessCodes.REISSUE_ACCESS_TOKEN_FAILURE_INVALID_REFRESH_TOKEN.getMessage()));
		verify(reissueAccessTokenUseCase, times(1)).invoke(any(ReissueAccessTokenUseCase.Command.class));
	}

}