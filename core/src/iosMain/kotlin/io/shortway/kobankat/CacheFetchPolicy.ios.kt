package io.shortway.kobankat

import cocoapods.PurchasesHybridCommon.RCCacheFetchPolicy
import cocoapods.PurchasesHybridCommon.RCCacheFetchPolicyCachedOrFetched
import cocoapods.PurchasesHybridCommon.RCCacheFetchPolicyFetchCurrent
import cocoapods.PurchasesHybridCommon.RCCacheFetchPolicyFromCacheOnly
import cocoapods.PurchasesHybridCommon.RCCacheFetchPolicyNotStaleCachedOrFetched

public actual enum class CacheFetchPolicy {
    CACHE_ONLY,
    FETCH_CURRENT,
    NOT_STALE_CACHED_OR_CURRENT,
    CACHED_OR_FETCHED,
    ;

    public actual companion object {
        public actual fun default(): CacheFetchPolicy = CACHED_OR_FETCHED
    }
}

internal fun CacheFetchPolicy.toRCCacheFetchPolicy(): RCCacheFetchPolicy =
    when (this) {
        CacheFetchPolicy.CACHE_ONLY -> RCCacheFetchPolicyFromCacheOnly
        CacheFetchPolicy.FETCH_CURRENT -> RCCacheFetchPolicyFetchCurrent
        CacheFetchPolicy.NOT_STALE_CACHED_OR_CURRENT -> RCCacheFetchPolicyNotStaleCachedOrFetched
        CacheFetchPolicy.CACHED_OR_FETCHED -> RCCacheFetchPolicyCachedOrFetched
    }
