@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package io.shortway.kobankat

import com.revenuecat.purchases.EntitlementInfos as RcEntitlementInfos

public actual typealias EntitlementInfos = RcEntitlementInfos

public actual val EntitlementInfos.all: Map<String, EntitlementInfo>
    get() = all
public actual val EntitlementInfos.verification: VerificationResult
    get() = verification