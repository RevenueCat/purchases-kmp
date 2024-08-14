package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.VerificationResult

@Suppress("unused", "UNUSED_VARIABLE")
private class VerificationResultAPI {

    fun check(result: VerificationResult) {
        when (result) {
            VerificationResult.VERIFIED,
            VerificationResult.NOT_REQUESTED,
            VerificationResult.VERIFIED_ON_DEVICE,
            VerificationResult.FAILED,
            -> {
            }
        }.exhaustive

        val isVerified: Boolean = result.isVerified
    }
}
