package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.revenuecat.purchases.ui.revenuecatui.PaywallFooter as AndroidPaywallFooter

/**
 * A Paywall footer.
 */
@Composable
public actual fun OriginalTemplatePaywallFooter(
    options: PaywallOptions,
    mainContent: @Composable ((PaddingValues) -> Unit)?,
): Unit =
    AndroidPaywallFooter(
        options = options.toAndroidPaywallOptions(),
        mainContent = mainContent
    )
