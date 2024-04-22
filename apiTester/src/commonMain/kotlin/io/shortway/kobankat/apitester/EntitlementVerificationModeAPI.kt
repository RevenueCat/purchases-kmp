package io.shortway.kobankat.apitester

import io.shortway.kobankat.EntitlementVerificationMode

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
