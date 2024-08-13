package com.revenuecat.purchases.kmp

/**
 * Specifies behavior for a caching API.
 */
public expect enum class CacheFetchPolicy {
    /**
     * Returns values from the cache, or throws an error if not available. It won't initiate a fetch.
     */
    CACHE_ONLY,

    /**
     * Ignore whether the cache has a value or not and fetch the most up-to-date data. This will return an
     *  error if the fetch request fails
     */
    FETCH_CURRENT,

    /**
     * Returns the cached data if available and not stale. If not available or stale,
     *  it fetches up-to-date data. Note that this won't return stale data even if fetch request fails
     */
    NOT_STALE_CACHED_OR_CURRENT,

    /**
     * (default) returns the cached data if available (even if stale). If not available, fetches
     *  up-to-date data. If cached data is stale, it initiates a fetch in the background.
     */
    CACHED_OR_FETCHED,

    ;

    public companion object {
        /**
         * Returns the default policy when no policy is provided.
         */
        public fun default(): CacheFetchPolicy
    }
}
