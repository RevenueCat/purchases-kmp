package io.shortway.kobankat

/**
 * Contains information about the replacement mode to use in case of a product upgrade.
 * Use the platform specific subclasses in each implementation.
 * @property name Identifier of the proration mode to be used
 */
public expect interface ReplacementMode {
    public val name: String
}