package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.PresentedOfferingContext as AndroidPresentedOfferingContext
import com.revenuecat.purchases.PresentedOfferingContext.TargetingContext as AndroidPresentedOfferingTargetingContext

public actual class PresentedOfferingContext(
    internal val wrapped: AndroidPresentedOfferingContext
) {
    public actual val offeringIdentifier: String = wrapped.offeringIdentifier
    public actual val placementIdentifier: String? = wrapped.placementIdentifier
    public actual val targetingContext: PresentedOfferingTargetingContext? =
        wrapped.targetingContext?.let { PresentedOfferingTargetingContext(it) }

}

public actual class PresentedOfferingTargetingContext(
    internal val wrapped: AndroidPresentedOfferingTargetingContext
) {
    public actual val revision: Int = wrapped.revision
    public actual val ruleId: String = wrapped.ruleId
}
