package com.titi.titi_auth.adapter.out.cache;

import static com.titi.titi_common_lib.constant.Constants.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthCacheKeys {
	AUTH_CODE("ac", 5 * 60 * SECONDS);

	private final String prefix;
	private final long timeToLive;
}
