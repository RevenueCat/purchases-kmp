package com.revenuecat.purchases.kmp.models

/**
 * App Store only. Represents a [StoreProductDiscount] that has been validated and is ready to be
 * used for a purchase.
 */
public interface PromotionalOffer {
    public val discount: StoreProductDiscount
}
