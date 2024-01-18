package com.titi.titi_common_lib.util;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RandomGeneratorTest {

	public static final String REGEX_NUMBERS = "[0-9]+";
	public static final String REGEX_LOWERCASE = "[a-z]+";
	public static final String REGEX_UPPERCASE = "[A-Z]+";
	public static final String REGEX_NUMBERS_LOWERCASE = "[0-9a-z]+";
	public static final String REGEX_NUMBERS_UPPERCASE = "[0-9A-Z]+";
	public static final String REGEX_LOWERCASE_UPPERCASE = "[a-zA-Z]+";
	public static final String REGEX_NUMBERS_LOWERCASE_UPPERCASE = "[0-9a-zA-Z]+";

	public static Stream<Arguments> getGenerateMethodParametersAndRegex() {
		return Stream.of(
			Arguments.of(true, false, false, REGEX_NUMBERS),
			Arguments.of(false, true, false, REGEX_LOWERCASE),
			Arguments.of(false, false, true, REGEX_UPPERCASE),
			Arguments.of(true, true, false, REGEX_NUMBERS_LOWERCASE),
			Arguments.of(true, false, true, REGEX_NUMBERS_UPPERCASE),
			Arguments.of(false, true, true, REGEX_LOWERCASE_UPPERCASE),
			Arguments.of(true, true, true, REGEX_NUMBERS_LOWERCASE_UPPERCASE)
		);
	}

	@ParameterizedTest
	@MethodSource(value = "getGenerateMethodParametersAndRegex")
	void generateTest(boolean includeNumber, boolean includeLowercase, boolean includeUppercase, String regex) {
		assertThat(RandomGenerator.generate(includeNumber, includeLowercase, includeUppercase, 10, true).matches(regex)).isTrue();
	}

	@ParameterizedTest
	@MethodSource(value = "getGenerateMethodParametersAndRegex")
	void generateTestDoesNotAllowDuplication(boolean includeNumber, boolean includeLowercase, boolean includeUppercase) {
		final String result = RandomGenerator.generate(includeNumber, includeLowercase, includeUppercase, 10, false);
		assertThat(result.length()).isEqualTo(result.chars().distinct().count());
	}

	@Test
	void generateTestWhenIncludeNoneThenThrowIllegalArgumentException() {
		assertThatCode(() -> RandomGenerator.generate(false, false, false, 10, true)).isInstanceOf(IllegalArgumentException.class);
	}

}
