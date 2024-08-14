package com.revenuecat.purchases.kmp

/**
 * Contains information about the replacement mode to use in case of a product upgrade.
 * Use the platform specific subclasses in each implementation.
 * Currently only used for Play store upgrades/downgrades
 * @property name Identifier of the proration mode to be used
 */
public interface ReplacementMode {
    public val name: String
}
