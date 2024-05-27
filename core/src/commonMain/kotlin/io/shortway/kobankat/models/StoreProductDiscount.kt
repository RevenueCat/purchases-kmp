package io.shortway.kobankat.models

/**
 * App Store only. Information about a subscription offer that you configured in App Store Connect.
 */
public expect class StoreProductDiscount

/**
 * The price of this product discount
 */
public expect fun StoreProductDiscount.price(parentProduct: StoreProduct): Price

/**
 * The number of periods the product discount is available. This is 1 for
 * [DiscountPaymentMode.PAY_UP_FRONT] and [DiscountPaymentMode.FREE_TRIAL],
 * but can be more than 1 for [DiscountPaymentMode.PAY_AS_YOU_GO].
 */
public expect val StoreProductDiscount.numberOfPeriods: Long

/**
 * A string used to uniquely identify a discount offer for a product.
 */
public expect val StoreProductDiscount.offerIdentifier: String?

/**
 * The payment mode for this product discount.
 */
public expect val StoreProductDiscount.paymentMode: DiscountPaymentMode

/**
 * The period for the product discount.
 */
public expect val StoreProductDiscount.subscriptionPeriod: Period

/**
 * The type of product discount.
 */
public expect val StoreProductDiscount.type: DiscountType
