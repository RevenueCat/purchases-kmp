package io.shortway.kobankat

import cocoapods.RevenueCat.RCVerificationResult
import cocoapods.RevenueCat.RCVerificationResultFailed
import cocoapods.RevenueCat.RCVerificationResultNotRequested
import cocoapods.RevenueCat.RCVerificationResultVerified
import cocoapods.RevenueCat.RCVerificationResultVerifiedOnDevice

public actual enum class VerificationResult {
    NOT_REQUESTED,
    VERIFIED,
    FAILED,
    VERIFIED_ON_DEVICE,
    ;
}

internal fun RCVerificationResult.toVerificationResult(): VerificationResult =
    when (this) {
        RCVerificationResultNotRequested -> VerificationResult.NOT_REQUESTED
        RCVerificationResultVerified -> VerificationResult.VERIFIED
        RCVerificationResultFailed -> VerificationResult.FAILED
        RCVerificationResultVerifiedOnDevice -> VerificationResult.VERIFIED_ON_DEVICE
        else -> error("Unexpected RCVerificationResult: $this")
    }