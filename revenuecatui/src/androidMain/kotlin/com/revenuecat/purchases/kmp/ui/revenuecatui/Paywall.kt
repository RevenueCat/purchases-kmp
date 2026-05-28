package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.PurchasesErrorCode
import com.revenuecat.purchases.ui.revenuecatui.ExperimentalPreviewRevenueCatUIPurchasesAPI
import com.revenuecat.purchases.ui.revenuecatui.Paywall as RcPaywall

@Composable
public actual fun Paywall(options: PaywallOptions): Unit {
    if (!Purchases.isConfigured) {
        LaunchedEffect(options) {
            options.listener?.onPurchaseError(
                PurchasesError(
                    code = PurchasesErrorCode.ConfigurationError,
                    underlyingErrorMessage = "Purchases is not configured. " +
                        "Call Purchases.configure(...) before showing Paywall."
                )
            )
            options.dismissRequest()
        }
        return
    }

    RcPaywall(options.toAndroidPaywallOptions())
}
