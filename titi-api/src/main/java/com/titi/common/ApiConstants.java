package com.titi.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiConstants {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Common {

		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static final class Model {

			public static final String DESCRIPTION = "공통 API 응답 모델";

		}

		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static final class Property {

			public static final String VALUE1 = "HTTP status code";
			public static final String EXAMPLE1 = "200";
			public static final String VALUE2 = "Business status code";
			public static final String EXAMPLE2 = "RM001";
			public static final String VALUE3 = "Description";
			public static final String EXAMPLE3 = "OK";

		}

	}

}
