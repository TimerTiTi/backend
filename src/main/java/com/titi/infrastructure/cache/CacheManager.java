package com.titi.infrastructure.cache;

import java.util.Optional;

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

	/**
	 * Retrieves the value associated with the specified key from the cache.
	 *
	 * @param key The unique key for the cache entry.
	 * @return An {@code Optional} containing the value associated with the given key,
	 *         or an empty {@code Optional} if the key is not found in the cache.
	 * @throws Exception If an error occurs while interacting with the cache server, including
	 *                   connection issues, timeouts, and so on.
	 */
	Optional<String> get(String key) throws Exception;

	/**
	 * Removes the cache entry associated with the specified key.
	 *
	 * @param key The unique key for the cache entry to be removed.
	 * @throws Exception If an error occurs while interacting with the cache server, including
	 *                   connection issues, timeouts, and so on.
	 */
	void remove(String key) throws Exception;
}
