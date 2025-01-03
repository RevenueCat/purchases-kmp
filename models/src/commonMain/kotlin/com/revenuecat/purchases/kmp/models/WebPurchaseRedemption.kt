package com.revenuecat.purchases.kmp.models

/**
 * Represents a web redemption link, that can be redeemed using [Purchases.redeemWebPurchase]
 */
public class WebPurchaseRedemption(
    public val redemptionUrl: String,
)
