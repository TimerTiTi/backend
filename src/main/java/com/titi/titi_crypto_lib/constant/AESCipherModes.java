package com.titi.titi_crypto_lib.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * See the Cipher section in the <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#cipher-algorithms">
 * Java Security Standard Algorithm Names Specification</a> for information about standard transformation names.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AESCipherModes {

	GCM_NO_PADDING("AES/GCM/NoPadding"),
	CTR_NO_PADDING("AES/CTR/NoPadding");

	private final String transformation;

}
