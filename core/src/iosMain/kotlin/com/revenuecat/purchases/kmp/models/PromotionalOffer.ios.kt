package com.revenuecat.purchases.kmp.models

import cocoapods.PurchasesHybridCommon.RCPromotionalOffer

public actual typealias PromotionalOffer = RCPromotionalOffer

public actual val PromotionalOffer.discount: StoreProductDiscount
    get() = discount()
