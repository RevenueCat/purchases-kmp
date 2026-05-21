package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.IntroEligibilityStatus
import com.revenuecat.purchases.kn.core.RCIntroEligibility
import com.revenuecat.purchases.kn.core.RCIntroEligibilityStatus
import com.revenuecat.purchases.kn.core.RCIntroEligibilityStatusEligible
import com.revenuecat.purchases.kn.core.RCIntroEligibilityStatusIneligible
import com.revenuecat.purchases.kn.core.RCIntroEligibilityStatusNoIntroOfferExists
import com.revenuecat.purchases.kn.core.RCIntroEligibilityStatusUnknown

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
