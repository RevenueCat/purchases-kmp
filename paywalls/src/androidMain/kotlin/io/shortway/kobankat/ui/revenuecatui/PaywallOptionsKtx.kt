package io.shortway.kobankat.ui.revenuecatui

import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.ui.revenuecatui.ExperimentalPreviewRevenueCatUIPurchasesAPI
import com.revenuecat.purchases.ui.revenuecatui.PaywallListener

@OptIn(ExperimentalPreviewRevenueCatUIPurchasesAPI::class)
internal fun PaywallOptions.toRcPaywallOptions(): com.revenuecat.purchases.ui.revenuecatui.PaywallOptions =
    com.revenuecat.purchases.ui.revenuecatui.PaywallOptions.Builder(dismissRequest)
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
