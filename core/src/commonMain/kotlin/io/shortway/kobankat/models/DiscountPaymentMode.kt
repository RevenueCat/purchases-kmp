package io.shortway.kobankat.models

/**
 * App Store only. The payment mode for a [StoreProductDiscount] indicates how the product discount
 * price is charged.
 */
public expect enum class DiscountPaymentMode {
    /**
     * No initial charge.
     */
    FREE_TRIAL,

    /**
     * Price is charged one or more times.
     */
    PAY_AS_YOU_GO,

    /**
     * Price is charged once in advance.
     */
    PAY_UP_FRONT,
}