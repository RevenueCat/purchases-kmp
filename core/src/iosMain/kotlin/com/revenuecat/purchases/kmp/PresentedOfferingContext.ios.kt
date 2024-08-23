package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCPresentedOfferingContext as IosPresentedOfferingContext
import cocoapods.PurchasesHybridCommon.RCTargetingContext as IosPresentedOfferingTargetingContext

internal fun IosPresentedOfferingContext.toPresentedOfferingContext() = PresentedOfferingContext(
    offeringIdentifier = offeringIdentifier(),
    placementIdentifier = placementIdentifier(),
    targetingContext = targetingContext()?.toPresentedOfferingTargetingContext()
)

internal fun IosPresentedOfferingTargetingContext.toPresentedOfferingTargetingContext() =
    PresentedOfferingTargetingContext(
        revision = revision().toInt(),
        ruleId = ruleId()
    )
