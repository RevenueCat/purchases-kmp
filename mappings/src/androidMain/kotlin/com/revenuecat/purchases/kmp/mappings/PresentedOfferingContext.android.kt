package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.PresentedOfferingTargetingContext
import com.revenuecat.purchases.PresentedOfferingContext as AndroidPresentedOfferingContext
import com.revenuecat.purchases.PresentedOfferingContext.TargetingContext as AndroidPresentedOfferingTargetingContext

internal fun AndroidPresentedOfferingContext.toPresentedOfferingContext() =
    PresentedOfferingContext(
        offeringIdentifier = offeringIdentifier,
        placementIdentifier = placementIdentifier,
        targetingContext = targetingContext?.toPresentedOfferingTargetingContext()
    )

internal fun AndroidPresentedOfferingTargetingContext.toPresentedOfferingTargetingContext() =
    PresentedOfferingTargetingContext(
        revision = revision,
        ruleId = ruleId
    )
