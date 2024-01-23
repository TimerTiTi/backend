package com.titi.titi_common_lib.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JacksonHelperTest {

	@Test
	void toJsonTestSuccess() {
		// given
		final TestObject testObject = new TestObject("value1", 42);

		// when
		final String jsonString = JacksonHelper.toJson(testObject);

		// then
		assertThat(jsonString).isEqualTo("{\"property1\":\"value1\",\"property2\":42}");
	}

	@Test
	void fromJsonTestSuccess() {
		// given
		final String jsonString = "{\"property1\":\"value1\",\"property2\":42}";
		final TestObject expectedObject = new TestObject("value1", 42);

		// when
		final TestObject resultObject = JacksonHelper.fromJson(jsonString, TestObject.class);

		// then
		assertThat(resultObject).isEqualTo(expectedObject);
	}

	@Test
	void fromJsonTestThrowsJsonProcessingException() {
		// given
		final String jsonString = "{\"property1\":\"value1\",\"property2\":42";

		// when/then
		assertThatCode(() -> JacksonHelper.fromJson(jsonString, TestObject.class))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Invalid json format.");
	}

	record TestObject(
		String property1,
		Integer property2
	) {

	}

}
