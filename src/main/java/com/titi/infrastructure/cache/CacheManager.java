package com.titi.infrastructure.cache;

public interface CacheManager {

	/**
	 * Puts a key-value pair into the cache with the specified time-to-live (TTL).
	 *
	 * @param key        The unique key for the cache entry.
	 * @param value      The value to be stored in the cache. Should be serialized to JSON if an object.
	 * @param timeToLive The time-to-live (TTL) duration for the cache entry in seconds.
	 * @throws Exception If an error occurs while interacting with the cache server, including
	 *                   connection issues, timeouts and so on.
	 */
	void put(String key, String value, long timeToLive) throws Exception;

}
