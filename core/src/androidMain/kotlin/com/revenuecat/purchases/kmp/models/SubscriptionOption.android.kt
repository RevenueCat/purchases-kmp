package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.models.SubscriptionOption as RcSubscriptionOption

public class AndroidSubscriptionOption(
    internal val wrapped: RcSubscriptionOption
): SubscriptionOption {
    public override val id: String = wrapped.id
    public override val pricingPhases: List<PricingPhase> =
        wrapped.pricingPhases.map { it.toPricingPhase() }
    public override val tags: List<String> = wrapped.tags
    @Deprecated(
        "Use presentedOfferingContext instead",
        replaceWith = ReplaceWith("presentedOfferingContext.offeringIdentifier")
    )
    @Suppress("DEPRECATION")
    public override val presentedOfferingIdentifier: String? = wrapped.presentedOfferingIdentifier
    public override val presentedOfferingContext: PresentedOfferingContext? =
        wrapped.presentedOfferingContext?.let { PresentedOfferingContext(it) }
    public override val purchasingData: PurchasingData =
        AndroidPurchasingData(wrapped.purchasingData)
}
