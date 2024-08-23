package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.EntitlementInfos
import com.revenuecat.purchases.EntitlementInfos as RcEntitlementInfos

internal fun RcEntitlementInfos.toEntitlementInfos(): EntitlementInfos =
    EntitlementInfos(
        all.mapValues { it.value.toEntitlementInfo() },
        verification.toVerificationResult()
    )
