package com.titi.springdoc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum APIVersions {
	VERSION_1_0_0("API v1.0.0");

	private final String version;
}
