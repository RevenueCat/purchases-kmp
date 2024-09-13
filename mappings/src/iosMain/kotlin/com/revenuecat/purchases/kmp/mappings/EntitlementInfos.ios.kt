package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCEntitlementInfo
import cocoapods.PurchasesHybridCommon.RCEntitlementInfos
import com.revenuecat.purchases.kmp.mappings.ktx.mapEntries
import com.revenuecat.purchases.kmp.models.EntitlementInfos

internal fun RCEntitlementInfos.toEntitlementInfos(): EntitlementInfos =
    EntitlementInfos(
        all().mapEntries {
            it.key as String to (it.value as RCEntitlementInfo).toEntitlementInfo()
        },
        verification().toVerificationResult()
    )
