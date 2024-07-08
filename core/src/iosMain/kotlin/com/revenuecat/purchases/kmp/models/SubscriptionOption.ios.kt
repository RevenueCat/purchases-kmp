package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.PresentedOfferingContext

public actual interface SubscriptionOption {
    public actual val id: String
    public actual val pricingPhases: List<PricingPhase>
    public actual val tags: List<String>
    public actual val presentedOfferingIdentifier: String?
    public actual val presentedOfferingContext: PresentedOfferingContext?
    public actual val purchasingData: PurchasingData
}
