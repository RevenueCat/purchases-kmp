package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.PresentedOfferingContext
import com.revenuecat.purchases.kmp.models.PresentedOfferingTargetingContext
import com.revenuecat.purchases.kn.core.RCPresentedOfferingContext as IosPresentedOfferingContext
import com.revenuecat.purchases.kn.core.RCTargetingContext as IosPresentedOfferingTargetingContext

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
