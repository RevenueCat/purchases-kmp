package io.shortway.kobankat.models

/**
 * App Store only. Represents a [StoreProductDiscount] that has been validated and is ready to be
 * used for a purchase.
 */
public expect class PromotionalOffer

public expect val PromotionalOffer.discount: StoreProductDiscount