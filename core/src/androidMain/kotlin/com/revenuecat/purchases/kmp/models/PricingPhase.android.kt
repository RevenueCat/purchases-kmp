@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.models.PricingPhase as AndroidPricingPhase

public actual typealias PricingPhase = AndroidPricingPhase

public actual val PricingPhase.billingPeriod: Period
    get() = billingPeriod

public actual val PricingPhase.recurrenceMode: RecurrenceMode
    get() = recurrenceMode.toRecurrenceMode()

public actual val PricingPhase.billingCycleCount: Int?
    get() = billingCycleCount

public actual val PricingPhase.price: Price
    get() = price

public actual val PricingPhase.offerPaymentMode: OfferPaymentMode?
    get() = offerPaymentMode
