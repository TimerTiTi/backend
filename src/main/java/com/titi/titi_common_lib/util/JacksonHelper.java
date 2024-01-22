package com.titi.titi_common_lib.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JacksonHelper {

	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	public static <T> String toJson(T object) {
		try {
			return JSON_MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Invalid json format.", e);
		}
	}

	public static <T> T fromJson(String json, Class<T> classType) {
		try {
			return JSON_MAPPER.readValue(json, classType);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Invalid json format.", e);
		}
	}

}
