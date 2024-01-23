package com.titi.titi_crypto_lib.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HashAlgorithms {
	SHA_256("SHA-256");

	private final String algorithm;
}
