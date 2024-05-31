package io.shortway.kobankat.ui.revenuecatui

import io.shortway.kobankat.CustomerInfo
import io.shortway.kobankat.Offering
import kotlin.jvm.JvmSynthetic

/**
 * Options for the Paywall.
 */
public class PaywallOptions private constructor(
    /**
     * Optional Offering object obtained through `getOfferings()`.
     */
    public val offering: Offering?,
    /**
     * Optional listener. Called when a restore has been completed. This may be called even if no
     * entitlements have been granted.
     */
    public val onRestoreCompleted: (customerInfo: CustomerInfo) -> Unit,
    /**
     * Dismiss the paywall, i.e. remove the view, navigate to another screen, etc. Will be called
     * when the close button is pressed (if enabled) or when a purchase succeeds.
     */
    public val dismissRequest: () -> Unit,
) {
    override fun toString(): String =
        "PaywallOptions(" +
                "offering=$offering, " +
                "onRestoreCompleted=$onRestoreCompleted, " +
                "dismissRequest=$dismissRequest" +
                ")"

    /**
     * Use this builder to create an instance of [PaywallOptions].
     */
    public class Builder(
        /**
         * Dismiss the paywall, i.e. remove the view, navigate to another screen, etc. Will be
         * called when the close button is pressed (if enabled) or when a purchase succeeds.
         */
        public var dismissRequest: () -> Unit
    ) {
        /**
         * Optional Offering object obtained through `getOfferings()`.
         */
        public var offering: Offering? = null

        /**
         * Optional listener. Called when a restore has been completed. This may be called even if
         * no entitlements have been granted.
         */
        public var onRestoreCompleted: (customerInfo: CustomerInfo) -> Unit = { }

        /**
         * Creates a [PaywallOptions] instance with the specified properties.
         */
        public fun build(): PaywallOptions = PaywallOptions(
            offering = offering,
            onRestoreCompleted = onRestoreCompleted,
            dismissRequest = dismissRequest
        )
    }

}

/**
 * Options for the Paywall.
 */
@JvmSynthetic
public fun PaywallOptions(
    dismissRequest: () -> Unit,
    builder: PaywallOptions.Builder.() -> Unit = { },
): PaywallOptions =
    PaywallOptions.Builder(dismissRequest)
        .apply(builder)
        .build()
