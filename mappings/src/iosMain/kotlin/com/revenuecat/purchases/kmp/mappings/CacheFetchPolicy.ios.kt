package com.revenuecat.purchases.kmp.mappings

import swiftPMImport.com.revenuecat.purchases.models.RCCacheFetchPolicyCachedOrFetched
import swiftPMImport.com.revenuecat.purchases.models.RCCacheFetchPolicyFetchCurrent
import swiftPMImport.com.revenuecat.purchases.models.RCCacheFetchPolicyFromCacheOnly
import swiftPMImport.com.revenuecat.purchases.models.RCCacheFetchPolicyNotStaleCachedOrFetched
import com.revenuecat.purchases.kmp.models.CacheFetchPolicy
import swiftPMImport.com.revenuecat.purchases.models.RCCacheFetchPolicy as IosCacheFetchPolicy

public fun CacheFetchPolicy.toIosCacheFetchPolicy(): IosCacheFetchPolicy =
    when (this) {
        CacheFetchPolicy.CACHE_ONLY -> RCCacheFetchPolicyFromCacheOnly
        CacheFetchPolicy.FETCH_CURRENT -> RCCacheFetchPolicyFetchCurrent
        CacheFetchPolicy.NOT_STALE_CACHED_OR_CURRENT -> RCCacheFetchPolicyNotStaleCachedOrFetched
        CacheFetchPolicy.CACHED_OR_FETCHED -> RCCacheFetchPolicyCachedOrFetched
    }
