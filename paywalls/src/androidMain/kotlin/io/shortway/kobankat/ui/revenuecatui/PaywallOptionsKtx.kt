package io.shortway.kobankat.ui.revenuecatui

import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.ui.revenuecatui.ExperimentalPreviewRevenueCatUIPurchasesAPI
import com.revenuecat.purchases.ui.revenuecatui.PaywallListener
import com.revenuecat.purchases.ui.revenuecatui.PaywallOptions as AndroidPaywallOptions

@OptIn(ExperimentalPreviewRevenueCatUIPurchasesAPI::class)
internal fun PaywallOptions.toAndroidPaywallOptions(): AndroidPaywallOptions {
    // Capture this with a different name, to avoid a recursive call in the PaywallListener.
    val onRestore = onRestoreCompleted
    return AndroidPaywallOptions.Builder(dismissRequest)
        .setOffering(offering)
        .setListener(
            object : PaywallListener {
                override fun onRestoreCompleted(customerInfo: CustomerInfo) {
                    super.onRestoreCompleted(customerInfo)
                    onRestore(customerInfo)
                }
            }
        )
        .build()
}
