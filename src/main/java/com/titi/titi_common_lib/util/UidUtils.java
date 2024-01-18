package com.titi.titi_common_lib.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UidUtils {

	/**
	 * Unit for the UNIX timestamp part (milliseconds since January 1, 1970) in the 19-digit Uid format.
	 */
	private static final long UID_TIMESTAMP_UNIT = 1_000_000;
	/**
	 * Maximum value for the random number part (6-digit) in the 19-digit Uid format.
	 */
	private static final int UID_RANDOM_MAX = 999_999;

	/**
	 * Generates a 19-digit Uid (Unique Identifier) by combining a 13-digit UNIX timestamp
	 * with a 6-digit random number.
	 * <p>
	 * The UNIX timestamp represents the number of milliseconds elapsed since January 1, 1970 (UTC).
	 * The random number adds variability to ensure uniqueness.
	 *
	 * @return A unique 19-digit Uid.
	 */
	public static long generateUid() {
		return generateUid(LocalDateTime.now(ZoneOffset.UTC));
	}

	/**
	 * Generates a 19-digit Uid based on the provided LocalDateTime by combining
	 * a 13-digit UNIX timestamp with a 6-digit random number.
	 * <p>
	 * The UNIX timestamp represents the number of milliseconds elapsed since January 1, 1970 (UTC).
	 * The random number adds variability to ensure uniqueness.
	 *
	 * @param dateTime The LocalDateTime to use for generating the Uid.
	 * @return A unique 19-digit Uid.
	 */
	public static long generateUid(LocalDateTime dateTime) {
		final String timestampArea = String.valueOf(dateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
		final String randomArea = get6DigitRandomNumberString();
		return Long.parseLong(timestampArea + randomArea);
	}

	/**
	 * Returns the minimum Uid (Unique Identifier) value that corresponds to the provided LocalDateTime.
	 * The minimum Uid is calculated by multiplying the UNIX timestamp (milliseconds since January 1, 1970) by 1,000,000.
	 *
	 * @param datetime The LocalDateTime for which the minimum Uid is calculated.
	 * @return The minimum Uid.
	 */
	public static long getMinUidByLocalDateTime(LocalDateTime datetime) {
		return getMinUidByUnixMilli(datetime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
	}

	/**
	 * Returns the maximum Uid (Unique Identifier) value that corresponds to the provided LocalDateTime.
	 * The maximum Uid is calculated by adding 999,999 to the minimum Uid.
	 *
	 * @param datetime The LocalDateTime for which the maximum Uid is calculated.
	 * @return The maximum Uid.
	 */
	public static long getMaxUidByLocalDateTime(LocalDateTime datetime) {
		return getMaxUidByUnixMilli(datetime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
	}

	/**
	 * Returns the minimum Uid value based on the provided UNIX timestamp (milliseconds since January 1, 1970).
	 * The minimum Uid is calculated by multiplying the UNIX timestamp by 1,000,000.
	 *
	 * @param unixTime The UNIX timestamp in milliseconds.
	 * @return The minimum Uid.
	 */
	public static long getMinUidByUnixMilli(long unixTime) {
		return unixTime * UID_TIMESTAMP_UNIT;
	}

	/**
	 * Returns the maximum Uid value based on the provided UNIX timestamp (milliseconds since January 1, 1970).
	 * The maximum Uid is calculated by adding 999,999 to the minimum Uid.
	 *
	 * @param unixTime The UNIX timestamp in milliseconds.
	 * @return The maximum Uid.
	 */
	public static long getMaxUidByUnixMilli(long unixTime) {
		return getMinUidByUnixMilli(unixTime) + UID_RANDOM_MAX;
	}

	/**
	 * Extracts the UNIX timestamp (milliseconds since January 1, 1970) from the given Uid.
	 *
	 * @param uid The Uid from which to extract the UNIX timestamp.
	 * @return The UNIX timestamp.
	 */
	public static long getUnixTimestampFromUid(long uid) {
		return uid / UID_TIMESTAMP_UNIT;
	}

	/**
	 * Converts the given Uid to a LocalDateTime representation in the UTC time zone.
	 *
	 * @param uid The Uid for which to retrieve the LocalDateTime representation.
	 * @return The LocalDateTime representation of the Uid.
	 */
	public static LocalDateTime getLocalDatetimeFromUid(long uid) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(getUnixTimestampFromUid(uid)), ZoneId.of("UTC"));
	}

	private static String get6DigitRandomNumberString() {
		return String.format("%06d", ThreadLocalRandom.current().nextInt(UID_RANDOM_MAX));
	}

}
