package com.revenuecat.purchases.kmp.models

/**
 * The VirtualCurrencies object contains all the virtual currencies associated to the user.
 */
public class VirtualCurrencies(
    /**
     * Map of all VirtualCurrency objects keyed by virtual currency code.
     */
    public val all: Map<String, VirtualCurrency>
) {
    /**
     * Returns the virtual currency for the given key, or null if it doesn't exist.
     *
     * @param code The code of the virtual currency to retrieve
     * @return The virtual currency, or null if not found
     */
    public operator fun get(code: String): VirtualCurrency? = all[code]
}
