package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCPresentedOfferingContext as IosPresentedOfferingContext
import cocoapods.PurchasesHybridCommon.RCTargetingContext as IosPresentedOfferingTargetingContext

public actual class PresentedOfferingContext(
    internal val wrapped: IosPresentedOfferingContext
) {
    public actual val offeringIdentifier: String = wrapped.offeringIdentifier()
    public actual val placementIdentifier: String? = wrapped.placementIdentifier()
    public actual val targetingContext: PresentedOfferingTargetingContext? =
        wrapped.targetingContext()?.let { PresentedOfferingTargetingContext(it) }

}

public actual class PresentedOfferingTargetingContext(
    internal val wrapped: IosPresentedOfferingTargetingContext
) {
    public actual val revision: Int = wrapped.revision().toInt()
    public actual val ruleId: String = wrapped.ruleId()
}
