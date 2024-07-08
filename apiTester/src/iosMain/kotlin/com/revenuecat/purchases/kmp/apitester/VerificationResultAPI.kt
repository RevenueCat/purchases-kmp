package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.VerificationResult
import com.revenuecat.purchases.kmp.isVerified

@Suppress("unused", "UNUSED_VARIABLE")
private class VerificationResultAPI {

    fun check(verificationResult: VerificationResult) {
        when (verificationResult) {
            VerificationResult.VERIFIED_ON_DEVICE,
            VerificationResult.NOT_REQUESTED,
            VerificationResult.VERIFIED,
            VerificationResult.FAILED,
            -> {
            }
        }.exhaustive

        val isVerified: Boolean = verificationResult.isVerified
    }
}
