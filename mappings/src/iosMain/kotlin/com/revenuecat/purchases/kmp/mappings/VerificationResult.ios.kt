package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.VerificationResult
import swiftPMImport.com.revenuecat.purchases.kn.core.RCVerificationResult as IosVerificationResult
import swiftPMImport.com.revenuecat.purchases.kn.core.RCVerificationResultFailed as IosVerificationResultFailed
import swiftPMImport.com.revenuecat.purchases.kn.core.RCVerificationResultNotRequested as IosVerificationResultNotRequested
import swiftPMImport.com.revenuecat.purchases.kn.core.RCVerificationResultVerified as IosVerificationResultVerified
import swiftPMImport.com.revenuecat.purchases.kn.core.RCVerificationResultVerifiedOnDevice as IosVerificationResultVerifiedOnDevice

internal fun IosVerificationResult.toVerificationResult(): VerificationResult =
    when (this) {
        IosVerificationResultNotRequested -> VerificationResult.NOT_REQUESTED
        IosVerificationResultVerified -> VerificationResult.VERIFIED
        IosVerificationResultFailed -> VerificationResult.FAILED
        IosVerificationResultVerifiedOnDevice -> VerificationResult.VERIFIED_ON_DEVICE
        else -> error("Unexpected RCVerificationResult: $this")
    }
