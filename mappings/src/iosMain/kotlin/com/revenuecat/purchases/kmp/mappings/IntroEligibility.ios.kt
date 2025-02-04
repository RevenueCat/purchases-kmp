package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCIntroEligibility
import cocoapods.PurchasesHybridCommon.RCIntroEligibilityStatus
import cocoapods.PurchasesHybridCommon.RCIntroEligibilityStatusEligible
import cocoapods.PurchasesHybridCommon.RCIntroEligibilityStatusIneligible
import cocoapods.PurchasesHybridCommon.RCIntroEligibilityStatusNoIntroOfferExists
import cocoapods.PurchasesHybridCommon.RCIntroEligibilityStatusUnknown
import com.revenuecat.purchases.kmp.models.IntroEligibility

public fun RCIntroEligibility.toIntroEligibility(): IntroEligibility =
    IntroEligibility(
        status = status().toIntroEligibilityStatus()
    )

private fun RCIntroEligibilityStatus.toIntroEligibilityStatus(): IntroEligibility.Status =
    when (this) {
        RCIntroEligibilityStatusUnknown -> IntroEligibility.Status.UNKNOWN
        RCIntroEligibilityStatusIneligible -> IntroEligibility.Status.INELIGIBLE
        RCIntroEligibilityStatusEligible -> IntroEligibility.Status.ELIGIBLE
        RCIntroEligibilityStatusNoIntroOfferExists -> IntroEligibility.Status.NO_INTRO_OFFER_EXISTS
        else -> error("Unexpected RCIntroEligibilityStatus: $this")
    }
