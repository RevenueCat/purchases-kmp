package com.revenuecat.purchases.kmp.models

/**
 * The VirtualCurrency object represents information about a virtual currency in the app.
 * Use this object to access information about a virtual currency, such as its current balance.
 */
public class VirtualCurrency(
    /**
     * The virtual currency's balance.
     */
    public val balance: Int,

    /**
     * The virtual currency's name.
     */
    public val name: String,

    /**
     * The virtual currency's code.
     */
    public val code: String,

    /**
     * The virtual currency's description defined in the RevenueCat dashboard.
     */
    public val serverDescription: String?
)
