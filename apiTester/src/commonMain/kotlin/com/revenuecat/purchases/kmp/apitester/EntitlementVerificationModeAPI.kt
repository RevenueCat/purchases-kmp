package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.EntitlementVerificationMode

@Suppress("unused")
private class EntitlementVerificationModeAPI {
    fun check(verificationMode: EntitlementVerificationMode) {
        when (verificationMode) {
            EntitlementVerificationMode.DISABLED,
            EntitlementVerificationMode.INFORMATIONAL,
                // Hidden ENFORCED mode during feature beta
                // EntitlementVerificationMode.ENFORCED
            -> {
            }
        }.exhaustive
    }
}
