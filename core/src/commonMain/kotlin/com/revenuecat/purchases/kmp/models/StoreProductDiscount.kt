package com.revenuecat.purchases.kmp.models

/**
 * App Store only. Information about a subscription offer that you configured in App Store Connect.
 */
public expect class StoreProductDiscount {

    /**
     * The price of this product discount
     */
    public val price: Price

    /**
     * The number of periods the product discount is available. This is 1 for
     * [DiscountPaymentMode.PAY_UP_FRONT] and [DiscountPaymentMode.FREE_TRIAL],
     * but can be more than 1 for [DiscountPaymentMode.PAY_AS_YOU_GO].
     */
    public val numberOfPeriods: Long

    /**
     * A string used to uniquely identify a discount offer for a product.
     */
    public val offerIdentifier: String?

    /**
     * The payment mode for this product discount.
     */
    public val paymentMode: DiscountPaymentMode

    /**
     * The period for the product discount.
     */
    public val subscriptionPeriod: Period

    /**
     * The type of product discount.
     */
    public val type: DiscountType
}
