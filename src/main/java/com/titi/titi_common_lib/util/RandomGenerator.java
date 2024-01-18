package com.titi.titi_common_lib.util;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RandomGenerator {

	private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
	private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBERS = "0123456789";

	/**
	 * Generates a random string based on specified criteria.
	 *
	 * @param includeNumber      		Whether to include numeric characters in the generated string.
	 * @param includeLowercase   		Whether to include lowercase alphabetic characters in the generated string.
	 * @param includeUppercase   		Whether to include uppercase alphabetic characters in the generated string.
	 * @param length             		The length of the generated string.
	 * @param allowsDuplication  		Whether duplication of characters is allowed in the generated string.
	 * @return                   		The randomly generated string based on the provided criteria.
	 * @throws IllegalArgumentException If none of the character sets (number, lowercase, uppercase) is included.
	 */
	public static String generate(boolean includeNumber, boolean includeLowercase, boolean includeUppercase, int length, boolean allowsDuplication) {
		validateParameters(includeNumber, includeLowercase, includeUppercase);
		return generateRandom(length, allowsDuplication, generateCharacters(includeNumber, includeLowercase, includeUppercase));
	}

	private static String generateRandom(int length, boolean allowsDuplication, StringBuilder characters) {
		final SecureRandom random = new SecureRandom();
		final StringBuilder result = new StringBuilder();

		if (allowsDuplication) {
			for (int i = 0; i < length; i++) {
				result.append(characters.charAt(random.nextInt(characters.length())));
			}
		} else {
			final Set<Character> uniqueCharacters = new HashSet<>();
			while (uniqueCharacters.size() < length) {
				uniqueCharacters.add(characters.charAt(random.nextInt(characters.length())));
			}
			for (char c : uniqueCharacters) {
				result.append(c);
			}
		}

		return result.toString();
	}

	private static void validateParameters(boolean includeNumber, boolean includeLowercase, boolean includeUppercase) {
		if (!includeNumber && !includeLowercase && !includeUppercase) {
			throw new IllegalArgumentException("At least one character set (number, lowercase, uppercase) must be included.");
		}
	}

	private static StringBuilder generateCharacters(boolean includeNumber, boolean includeLowercase, boolean includeUppercase) {
		final StringBuilder characters = new StringBuilder();
		if (includeNumber) {
			characters.append(NUMBERS);
		}
		if (includeLowercase) {
			characters.append(LOWERCASE_CHARACTERS);
		}
		if (includeUppercase) {
			characters.append(UPPERCASE_CHARACTERS);
		}
		return characters;
	}

}
