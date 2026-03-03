package com.revenuecat.purchases.kmp.sample

import androidx.compose.ui.unit.dp

internal val DefaultPaddingHorizontal = 4.dp
internal val DefaultSpacingVertical = 4.dp

// Set this to true to configure the SDK with purchasesAreCompletedBy = myApp.
// When enabled, PaywallView will use SamplePurchaseLogic to handle purchases
// and restores instead of the default RevenueCat flow.
internal const val purchasesAreCompletedByMyApp = false
