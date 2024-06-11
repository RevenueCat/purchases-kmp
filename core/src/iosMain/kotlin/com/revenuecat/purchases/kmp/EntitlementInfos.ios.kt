package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCEntitlementInfo
import cocoapods.PurchasesHybridCommon.RCEntitlementInfos
import com.revenuecat.purchases.kmp.ktx.mapEntries

public actual typealias EntitlementInfos = RCEntitlementInfos

public actual val EntitlementInfos.all: Map<String, EntitlementInfo>
    get() = all().mapEntries { (id, info) -> id as String to (info as RCEntitlementInfo) }
public actual val EntitlementInfos.verification: VerificationResult
    get() = verification().toVerificationResult()
