package com.titi.titi_crypto_lib.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HashingUtilsTest {

	@Test
	void hashSha256Test() {
		// given
		String input1 = "abc";
		String input2 = "123";
		String expectedHash = "EI3BZoVOnkkhT+IXt1puO7Jlr361g/n/2fPqP4g2Q+A=";

		// when
		String result = HashingUtils.hashSha256(input1, input2);

		// then
		assertThat(result).isEqualTo(expectedHash);
	}

}
