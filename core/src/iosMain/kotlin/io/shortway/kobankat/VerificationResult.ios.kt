package io.shortway.kobankat

import cocoapods.PurchasesHybridCommon.RCVerificationResult
import cocoapods.PurchasesHybridCommon.RCVerificationResultFailed
import cocoapods.PurchasesHybridCommon.RCVerificationResultNotRequested
import cocoapods.PurchasesHybridCommon.RCVerificationResultVerified
import cocoapods.PurchasesHybridCommon.RCVerificationResultVerifiedOnDevice

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
