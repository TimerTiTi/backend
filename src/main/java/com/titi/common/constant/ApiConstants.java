package com.titi.common.constant;

import org.springframework.http.MediaType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiConstants {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Common {

		public static final String RESPONSE_DESCRIPTION = "Common Api Response Model";
		public static final String RESPONSE_PROPERTY_DESCRIPTION1 = "HTTP Status Code";
		public static final String RESPONSE_PROPERTY_EXAMPLE1 = "200";
		public static final String RESPONSE_PROPERTY_DESCRIPTION2 = "Business Status Code";
		public static final String RESPONSE_PROPERTY_EXAMPLE2 = "RM001";
		public static final String RESPONSE_PROPERTY_DESCRIPTION3 = "Description";
		public static final String RESPONSE_PROPERTY_EXAMPLE3 = "OK";

	}

	/**
	 * Example
	 */
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class SignUpApis {

		public static final String NAME = "Sign up APIs";
		public static final String DESCRIPTION = "APIs for registering a new member with the TiTi service";

		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static final class RegularSignUpApi {

			public static final String NAME = "Regular sign up API";
			public static final String DESCRIPTION = "";
			public static final String URI = "";
			public static final String PRODUCE = MediaType.APPLICATION_JSON_VALUE;
			public static final String CONSUME = MediaType.APPLICATION_JSON_VALUE;

			public static final String REQUEST_MODEL_DESCRIPTION = "";
			public static final String REQUEST_PARAM_DESCRIPTION1 = "";
			public static final String REQUEST_PARAM_EXAMPLE1 = "";
			public static final boolean REQUEST_PARAM_MANDATORY1 = true;

			public static final String RESPONSE_MODEL_DESCRIPTION = "";
			public static final String RESPONSE_PARAM_DESCRIPTION1 = "";
			public static final String RESPONSE_PARAM_EXAMPLE1 = "";

		}

	}

}
