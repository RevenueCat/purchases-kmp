package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.CacheFetchPolicy
import swiftPMImport.com.revenuecat.purchases.kn.core.RCCacheFetchPolicyCachedOrFetched
import swiftPMImport.com.revenuecat.purchases.kn.core.RCCacheFetchPolicyFetchCurrent
import swiftPMImport.com.revenuecat.purchases.kn.core.RCCacheFetchPolicyFromCacheOnly
import swiftPMImport.com.revenuecat.purchases.kn.core.RCCacheFetchPolicyNotStaleCachedOrFetched
import swiftPMImport.com.revenuecat.purchases.kn.core.RCCacheFetchPolicy as IosCacheFetchPolicy

public fun CacheFetchPolicy.toIosCacheFetchPolicy(): IosCacheFetchPolicy =
    when (this) {
        CacheFetchPolicy.CACHE_ONLY -> RCCacheFetchPolicyFromCacheOnly
        CacheFetchPolicy.FETCH_CURRENT -> RCCacheFetchPolicyFetchCurrent
        CacheFetchPolicy.NOT_STALE_CACHED_OR_CURRENT -> RCCacheFetchPolicyNotStaleCachedOrFetched
        CacheFetchPolicy.CACHED_OR_FETCHED -> RCCacheFetchPolicyCachedOrFetched
    }
