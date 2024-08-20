package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.PresentedOfferingContext

public class IosSubscriptionOption(
    public override val id: String,
    public override val pricingPhases: List<PricingPhase>,
    public override val tags: List<String>,
    @Deprecated(
        "Use presentedOfferingContext instead",
        replaceWith = ReplaceWith("presentedOfferingContext.offeringIdentifier")
    )
    public override val presentedOfferingIdentifier: String?,
    public override val presentedOfferingContext: PresentedOfferingContext?,
    public override val purchasingData: PurchasingData,
): SubscriptionOption