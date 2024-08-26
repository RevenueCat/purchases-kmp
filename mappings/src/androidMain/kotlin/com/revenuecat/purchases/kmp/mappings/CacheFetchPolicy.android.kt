package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.CacheFetchPolicy
import com.revenuecat.purchases.CacheFetchPolicy as AndroidCacheFetchPolicy

public fun CacheFetchPolicy.toAndroidCacheFetchPolicy(): AndroidCacheFetchPolicy =
    when (this) {
        CacheFetchPolicy.CACHE_ONLY -> AndroidCacheFetchPolicy.CACHE_ONLY
        CacheFetchPolicy.FETCH_CURRENT -> AndroidCacheFetchPolicy.FETCH_CURRENT
        CacheFetchPolicy.NOT_STALE_CACHED_OR_CURRENT -> AndroidCacheFetchPolicy.NOT_STALE_CACHED_OR_CURRENT
        CacheFetchPolicy.CACHED_OR_FETCHED -> AndroidCacheFetchPolicy.CACHED_OR_FETCHED
    }
