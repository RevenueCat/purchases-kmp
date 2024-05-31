package io.shortway.kobankat.ui.revenuecatui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.revenuecat.purchases.ui.revenuecatui.ExperimentalPreviewRevenueCatUIPurchasesAPI
import com.revenuecat.purchases.ui.revenuecatui.PaywallFooter as RcPaywallFooter

/**
 * A Paywall footer.
 */
@OptIn(ExperimentalPreviewRevenueCatUIPurchasesAPI::class)
@Composable
public actual fun PaywallFooter(
    options: PaywallOptions,
    condensed: Boolean,
    mainContent: @Composable ((PaddingValues) -> Unit)?,
): Unit =
    RcPaywallFooter(
        options = options.toRcPaywallOptions(),
        condensed = condensed,
        mainContent = mainContent
    )
