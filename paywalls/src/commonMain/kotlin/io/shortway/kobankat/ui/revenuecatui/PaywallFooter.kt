package io.shortway.kobankat.ui.revenuecatui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

/**
 * A Paywall footer.
 */
@Composable
public expect fun PaywallFooter(
    options: PaywallOptions,
    condensed: Boolean = false,
    mainContent: @Composable ((PaddingValues) -> Unit)? = null,
)
