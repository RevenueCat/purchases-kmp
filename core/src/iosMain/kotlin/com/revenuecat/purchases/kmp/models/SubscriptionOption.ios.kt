package com.revenuecat.purchases.kmp.models

public actual interface SubscriptionOption {
    public actual val id: String
    public actual val pricingPhases: List<PricingPhase>
    public actual val tags: List<String>
    public actual val presentedOfferingIdentifier: String?
    public actual val purchasingData: PurchasingData
}
