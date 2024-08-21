package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.models.SubscriptionOption as RcSubscriptionOption

internal class AndroidSubscriptionOption(
    internal val wrapped: RcSubscriptionOption
): SubscriptionOption {
    override val id: String = wrapped.id
    override val pricingPhases: List<PricingPhase> =
        wrapped.pricingPhases.map { it.toPricingPhase() }
    override val tags: List<String> = wrapped.tags
    @Deprecated(
        "Use presentedOfferingContext instead",
        replaceWith = ReplaceWith("presentedOfferingContext.offeringIdentifier")
    )
    @Suppress("DEPRECATION")
    override val presentedOfferingIdentifier: String? = wrapped.presentedOfferingIdentifier
    override val presentedOfferingContext: PresentedOfferingContext? =
        wrapped.presentedOfferingContext?.let { PresentedOfferingContext(it) }
    override val purchasingData: PurchasingData =
        AndroidPurchasingData(wrapped.purchasingData)
}
