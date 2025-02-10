package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

/**
 * Composable offering a minified screen Paywall UI configured from the RevenueCat dashboard.
 * You can pass in your own Composables to be displayed in the main content area.
 * Only compatible with original Paywalls. If using V2 Paywalls, this will display a fallback paywall instead.
 */
@Composable
public expect fun OriginalTemplatePaywallFooter(
    options: PaywallOptions,
    mainContent: @Composable ((PaddingValues) -> Unit)? = null,
)

/**
 * Composable offering a minified screen Paywall UI configured from the RevenueCat dashboard.
 * You can pass in your own Composables to be displayed in the main content area.
 * Only compatible with original Paywalls. If using V2 Paywalls, this will display a fallback paywall instead.
 */
@Deprecated(
    "Use OriginalTemplatePaywallFooter instead",
    ReplaceWith("OriginalTemplatePaywallFooter(options, mainContent)"),
)
@Composable
public fun PaywallFooter(
    options: PaywallOptions,
    mainContent: @Composable ((PaddingValues) -> Unit)? = null,
) {
    OriginalTemplatePaywallFooter(options, mainContent)
}
