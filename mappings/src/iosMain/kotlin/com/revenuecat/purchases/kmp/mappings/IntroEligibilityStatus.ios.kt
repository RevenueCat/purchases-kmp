package com.revenuecat.purchases.kmp.mappings

import cocoapods.PurchasesHybridCommon.RCIntroEligibility
import cocoapods.PurchasesHybridCommon.RCIntroEligibilityStatus
import cocoapods.PurchasesHybridCommon.RCIntroEligibilityStatusEligible
import cocoapods.PurchasesHybridCommon.RCIntroEligibilityStatusIneligible
import cocoapods.PurchasesHybridCommon.RCIntroEligibilityStatusNoIntroOfferExists
import cocoapods.PurchasesHybridCommon.RCIntroEligibilityStatusUnknown
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
