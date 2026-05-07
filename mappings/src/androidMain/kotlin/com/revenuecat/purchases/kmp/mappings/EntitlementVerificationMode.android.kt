package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.EntitlementVerificationMode
import com.revenuecat.purchases.EntitlementVerificationMode as AndroidEntitlementVerificationMode

public fun EntitlementVerificationMode.toAndroidEntitlementVerificationMode(): AndroidEntitlementVerificationMode =
    when (this) {
        EntitlementVerificationMode.DISABLED -> AndroidEntitlementVerificationMode.DISABLED
        EntitlementVerificationMode.INFORMATIONAL -> AndroidEntitlementVerificationMode.INFORMATIONAL
    }
