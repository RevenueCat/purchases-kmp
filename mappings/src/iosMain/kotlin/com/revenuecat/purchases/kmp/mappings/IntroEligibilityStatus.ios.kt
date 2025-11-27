package com.revenuecat.purchases.kmp.mappings

import swiftPMImport.com.revenuecat.purchases.models.RCIntroEligibility
import swiftPMImport.com.revenuecat.purchases.models.RCIntroEligibilityStatus
import swiftPMImport.com.revenuecat.purchases.models.RCIntroEligibilityStatusEligible
import swiftPMImport.com.revenuecat.purchases.models.RCIntroEligibilityStatusIneligible
import swiftPMImport.com.revenuecat.purchases.models.RCIntroEligibilityStatusNoIntroOfferExists
import swiftPMImport.com.revenuecat.purchases.models.RCIntroEligibilityStatusUnknown
import com.revenuecat.purchases.kmp.models.IntroEligibilityStatus

public fun RCIntroEligibility.toIntroEligibilityStatus(): IntroEligibilityStatus =
    status().toIntroEligibilityStatus()

private fun RCIntroEligibilityStatus.toIntroEligibilityStatus(): IntroEligibilityStatus =
    when (this) {
        RCIntroEligibilityStatusUnknown -> IntroEligibilityStatus.UNKNOWN
        RCIntroEligibilityStatusIneligible -> IntroEligibilityStatus.INELIGIBLE
        RCIntroEligibilityStatusEligible -> IntroEligibilityStatus.ELIGIBLE
        RCIntroEligibilityStatusNoIntroOfferExists -> IntroEligibilityStatus.NO_INTRO_OFFER_EXISTS
        else -> error("Unexpected RCIntroEligibilityStatus: $this")
    }
