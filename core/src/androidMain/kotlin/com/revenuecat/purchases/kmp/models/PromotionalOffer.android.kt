package com.revenuecat.purchases.kmp.models

public actual class PromotionalOffer private constructor()

public actual val PromotionalOffer.discount: StoreProductDiscount
    get() = error("PromotionalOffer is iOS-only.")
