package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.mappings.ktx.mapEntries
import com.revenuecat.purchases.kmp.models.EntitlementInfos
import swiftPMImport.com.revenuecat.purchases.kn.core.RCEntitlementInfo
import swiftPMImport.com.revenuecat.purchases.kn.core.RCEntitlementInfos

internal fun RCEntitlementInfos.toEntitlementInfos(): EntitlementInfos =
    EntitlementInfos(
        all().mapEntries {
            it.key as String to (it.value as RCEntitlementInfo).toEntitlementInfo()
        },
        verification().toVerificationResult()
    )
