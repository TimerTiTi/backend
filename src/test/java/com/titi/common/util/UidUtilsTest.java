package com.titi.common.util;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class UidUtilsTest {

	private static final long UID_TIMESTAMP_UNIT = 1_000_000;

	@Test
	void generateUidTestUidShouldBe19Digits() {
		assertThat(Long.toString(UidUtils.generateUid()).length()).isEqualTo(19);
	}

	@Test
	void getMinUidByLocalDateTimeTest() {
		final LocalDateTime datetime = LocalDateTime.of(2022, 5, 1, 0, 0);
		assertThat(UidUtils.getMinUidByLocalDateTime(datetime)).isEqualTo(1651363200000000000L);
	}

	@Test
	void getMaxUidByLocalDateTimeTest() {
		final LocalDateTime datetime = LocalDateTime.of(2022, 5, 1, 0, 0);
		assertThat(UidUtils.getMaxUidByLocalDateTime(datetime)).isEqualTo(1651363200000999999L);
	}

	@Test
	void getUnixTimestampFromUidTest() {
		final long uid = UidUtils.generateUid(LocalDateTime.now());
		final long unixTimestampFromUid = UidUtils.getUnixTimestampFromUid(uid);
		assertThat(Long.toString(unixTimestampFromUid).substring(0, 13)).isEqualTo(Long.toString(uid).substring(0, 13));
	}

	@Test
	void getLocalDatetimeFromUidTest() {
		final long uid = UidUtils.generateUid(LocalDateTime.now());
		final LocalDateTime localDateTimeFromUid = UidUtils.getLocalDatetimeFromUid(uid);
		assertThat(Long.toString(UidUtils.generateUid(localDateTimeFromUid)).substring(0, 13)).isEqualTo(Long.toString(uid).substring(0, 13));
	}

}
