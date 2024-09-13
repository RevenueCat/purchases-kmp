package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.EntitlementInfos
import com.revenuecat.purchases.EntitlementInfos as RcEntitlementInfos

internal fun RcEntitlementInfos.toEntitlementInfos(): EntitlementInfos =
    EntitlementInfos(
        all = all.mapValues { it.value.toEntitlementInfo() },
        verification = verification.toVerificationResult()
    )
