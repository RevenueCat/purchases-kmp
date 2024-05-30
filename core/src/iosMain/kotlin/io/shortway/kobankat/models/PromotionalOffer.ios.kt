package io.shortway.kobankat.models

import cocoapods.PurchasesHybridCommon.RCPromotionalOffer

public actual typealias PromotionalOffer = RCPromotionalOffer

public actual val PromotionalOffer.discount: StoreProductDiscount
    get() = discount()
