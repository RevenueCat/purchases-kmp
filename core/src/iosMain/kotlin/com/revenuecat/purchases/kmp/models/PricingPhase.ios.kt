package com.revenuecat.purchases.kmp.models

public actual class PricingPhase

public actual val PricingPhase.billingPeriod: Period
    get() = error("Not available in iOS")

public actual val PricingPhase.recurrenceMode: RecurrenceMode
    get() = error("Not available in iOS")

public actual val PricingPhase.billingCycleCount: Int?
    get() = error("Not available in iOS")

public actual val PricingPhase.price: Price
    get() = error("Not available in iOS")

public actual val PricingPhase.offerPaymentMode: OfferPaymentMode?
    get() = error("Not available in iOS")
