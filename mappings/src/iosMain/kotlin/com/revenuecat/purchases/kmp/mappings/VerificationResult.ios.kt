package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.VerificationResult
import cocoapods.PurchasesHybridCommon.RCVerificationResult as IosVerificationResult
import cocoapods.PurchasesHybridCommon.RCVerificationResultFailed as IosVerificationResultFailed
import cocoapods.PurchasesHybridCommon.RCVerificationResultNotRequested as IosVerificationResultNotRequested
import cocoapods.PurchasesHybridCommon.RCVerificationResultVerified as IosVerificationResultVerified
import cocoapods.PurchasesHybridCommon.RCVerificationResultVerifiedOnDevice as IosVerificationResultVerifiedOnDevice

internal fun IosVerificationResult.toVerificationResult(): VerificationResult =
    when (this) {
        IosVerificationResultNotRequested -> VerificationResult.NOT_REQUESTED
        IosVerificationResultVerified -> VerificationResult.VERIFIED
        IosVerificationResultFailed -> VerificationResult.FAILED
        IosVerificationResultVerifiedOnDevice -> VerificationResult.VERIFIED_ON_DEVICE
        else -> error("Unexpected RCVerificationResult: $this")
    }
