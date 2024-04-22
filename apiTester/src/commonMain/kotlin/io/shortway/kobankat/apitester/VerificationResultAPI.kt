package io.shortway.kobankat.apitester

import io.shortway.kobankat.VerificationResult
import io.shortway.kobankat.isVerified

@Suppress("unused", "UNUSED_VARIABLE")
private class VerificationResultAPI {

    fun check(verificationResult: VerificationResult) {
        val isVerified: Boolean = verificationResult.isVerified
    }
}
