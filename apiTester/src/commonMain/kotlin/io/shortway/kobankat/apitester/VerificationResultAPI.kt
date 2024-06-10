package io.shortway.kobankat.apitester

import com.revenuecat.purchases.kmp.VerificationResult
import com.revenuecat.purchases.kmp.isVerified

@Suppress("unused", "UNUSED_VARIABLE")
private class VerificationResultAPI {

    fun check(verificationResult: VerificationResult) {
        val isVerified: Boolean = verificationResult.isVerified
    }
}
