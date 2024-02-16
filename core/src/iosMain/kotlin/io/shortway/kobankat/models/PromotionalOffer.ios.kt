package io.shortway.kobankat.models

import cocoapods.RevenueCat.RCPromotionalOffer

public actual typealias PromotionalOffer = RCPromotionalOffer

public actual val PromotionalOffer.discount: StoreProductDiscount
    get() = discount()