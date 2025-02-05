package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

/**
 * A Paywall footer.
 */
@Composable
public expect fun OriginalTemplatePaywallFooter(
    options: PaywallOptions,
    mainContent: @Composable ((PaddingValues) -> Unit)? = null,
)

/**
 * A Paywall footer.
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
