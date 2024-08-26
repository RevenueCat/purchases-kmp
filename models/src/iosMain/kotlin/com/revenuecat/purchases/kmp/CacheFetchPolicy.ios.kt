package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCCacheFetchPolicyCachedOrFetched
import cocoapods.PurchasesHybridCommon.RCCacheFetchPolicyFetchCurrent
import cocoapods.PurchasesHybridCommon.RCCacheFetchPolicyFromCacheOnly
import cocoapods.PurchasesHybridCommon.RCCacheFetchPolicyNotStaleCachedOrFetched
import cocoapods.PurchasesHybridCommon.RCCacheFetchPolicy as IosCacheFetchPolicy

internal fun CacheFetchPolicy.toIosCacheFetchPolicy(): IosCacheFetchPolicy =
    when (this) {
        CacheFetchPolicy.CACHE_ONLY -> RCCacheFetchPolicyFromCacheOnly
        CacheFetchPolicy.FETCH_CURRENT -> RCCacheFetchPolicyFetchCurrent
        CacheFetchPolicy.NOT_STALE_CACHED_OR_CURRENT -> RCCacheFetchPolicyNotStaleCachedOrFetched
        CacheFetchPolicy.CACHED_OR_FETCHED -> RCCacheFetchPolicyCachedOrFetched
    }
