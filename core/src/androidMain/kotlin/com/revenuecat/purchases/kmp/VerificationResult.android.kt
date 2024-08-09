package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.VerificationResult as AndroidVerificationResult

internal fun AndroidVerificationResult.toVerificationResult(): VerificationResult =
    when (this) {
        AndroidVerificationResult.NOT_REQUESTED -> VerificationResult.NOT_REQUESTED
        AndroidVerificationResult.VERIFIED -> VerificationResult.VERIFIED
        AndroidVerificationResult.FAILED -> VerificationResult.FAILED
        AndroidVerificationResult.VERIFIED_ON_DEVICE -> VerificationResult.VERIFIED_ON_DEVICE
    }
