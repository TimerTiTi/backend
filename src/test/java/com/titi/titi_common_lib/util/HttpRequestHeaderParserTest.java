package com.titi.titi_common_lib.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HttpRequestHeaderParserTest {

	@Nested
	class ParseSystemInformationFromUserAgent {

		@Test
		void successfulScenario() {
			// given
			final String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 13_5_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.1 Mobile/15E148 Safari/604.1";

			// when
			final String systemInformation = HttpRequestHeaderParser.parseSystemInformationFromUserAgent(userAgent);

			// then
			assertThat(systemInformation).isNotNull();
			assertThat(systemInformation).isEqualTo("iPhone; CPU iPhone OS 13_5_1 like Mac OS X");
		}

		@ParameterizedTest
		@ValueSource(strings = {"Mozilla/5.0", "Mozilla/4.0 look-alike"})
		void failureScenario(String userAgent) {
			// when
			final String systemInformation = HttpRequestHeaderParser.parseSystemInformationFromUserAgent(userAgent);

			// then
			assertThat(systemInformation).isNull();
		}

	}

}