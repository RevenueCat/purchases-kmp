package io.shortway.kobankat

import cocoapods.RevenueCat.RCEntitlementInfo
import cocoapods.RevenueCat.RCEntitlementInfos
import io.shortway.kobankat.ktx.mapEntries

public actual typealias EntitlementInfos = RCEntitlementInfos

public actual val EntitlementInfos.all: Map<String, EntitlementInfo>
    get() = all().mapEntries { (id, info) -> id as String to (info as RCEntitlementInfo) }
public actual val EntitlementInfos.verification: VerificationResult
    get() = verification().toVerificationResult()