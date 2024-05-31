package io.shortway.kobankat.ui.revenuecatui

import androidx.compose.runtime.Composable
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.ui.revenuecatui.ExperimentalPreviewRevenueCatUIPurchasesAPI
import com.revenuecat.purchases.ui.revenuecatui.PaywallListener
import com.revenuecat.purchases.ui.revenuecatui.Paywall as RcPaywall
import com.revenuecat.purchases.ui.revenuecatui.PaywallOptions as RcPaywallOptions

@OptIn(ExperimentalPreviewRevenueCatUIPurchasesAPI::class)
@Composable
public actual fun Paywall(options: PaywallOptions): Unit =
    RcPaywall(options.toRcPaywallOptions())

@OptIn(ExperimentalPreviewRevenueCatUIPurchasesAPI::class)
private fun PaywallOptions.toRcPaywallOptions(): RcPaywallOptions =
    RcPaywallOptions.Builder(dismissRequest)
        .setOffering(offering)
        .setListener(
            object : PaywallListener {
                override fun onRestoreCompleted(customerInfo: CustomerInfo) {
                    super.onRestoreCompleted(customerInfo)
                    onRestoreCompleted(customerInfo)
                }
            }
        )
        .build()
