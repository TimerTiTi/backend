package com.titi.titi_auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TargetType {
	EMAIL("E");

	private final String shortenName;
}
