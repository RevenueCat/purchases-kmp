package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.EntitlementVerificationMode
import com.revenuecat.purchases.kn.core.RCEntitlementVerificationMode
import com.revenuecat.purchases.kn.core.RCEntitlementVerificationModeDisabled
import com.revenuecat.purchases.kn.core.RCEntitlementVerificationModeInformational

public fun EntitlementVerificationMode.toIosEntitlementVerificationMode(): RCEntitlementVerificationMode =
    when (this) {
        EntitlementVerificationMode.DISABLED -> RCEntitlementVerificationModeDisabled
        EntitlementVerificationMode.INFORMATIONAL -> RCEntitlementVerificationModeInformational
    }
