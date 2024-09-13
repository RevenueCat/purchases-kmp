package com.revenuecat.purchases.kmp.ui.revenuecatui

import com.revenuecat.purchases.kmp.models.Offering
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
     * Whether to display a close button on the paywall screen. Only available when using
     * [Paywall]. Ignored when using [PaywallFooter]. Defaults to false.
     */
    public val shouldDisplayDismissButton: Boolean,
    /**
     * Optional listener, called for various purchase and restore events.
     */
    public val listener: PaywallListener?,
    /**
     * Dismiss the paywall, i.e. remove the view, navigate to another screen, etc. Will be called
     * when the close button is pressed (if enabled) or when a purchase succeeds.
     */
    public val dismissRequest: () -> Unit,
) {
    override fun toString(): String =
        "PaywallOptions(" +
                "offering=$offering, " +
                "shouldDisplayDismissButton=$shouldDisplayDismissButton, " +
                "listener=$listener, " +
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
         * Whether to display a close button on the paywall screen. Only available when using
         * [Paywall]. Ignored when using [PaywallFooter]. Defaults to false.
         */
        public var shouldDisplayDismissButton: Boolean = false

        /**
         * Optional listener, called for various purchase and restore events.
         */
        public var listener: PaywallListener? = null

        /**
         * Creates a [PaywallOptions] instance with the specified properties.
         */
        public fun build(): PaywallOptions = PaywallOptions(
            offering = offering,
            shouldDisplayDismissButton = shouldDisplayDismissButton,
            listener = listener,
            dismissRequest = dismissRequest,
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
