package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.Composable
import com.revenuecat.purchases.ui.revenuecatui.ExperimentalPreviewRevenueCatUIPurchasesAPI
import com.revenuecat.purchases.ui.revenuecatui.Paywall as RcPaywall

@OptIn(ExperimentalPreviewRevenueCatUIPurchasesAPI::class)
@Composable
public actual fun Paywall(options: PaywallOptions): Unit =
    RcPaywall(options.toAndroidPaywallOptions())
