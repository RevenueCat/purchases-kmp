package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.Offering
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallListener
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions


@Suppress("unused", "UNUSED_VARIABLE", "RedundantExplicitType")
private class PaywallOptionsAPI {
    fun check(offering: Offering?, listener: PaywallListener?) {
        val options: PaywallOptions = PaywallOptions(
            dismissRequest = {}
        ) {
            this.offering = offering
            this.shouldDisplayDismissButton = true
            this.listener = listener
        }
        val listener2: PaywallListener? = options.listener
        val dismissRequest: () -> Unit = options.dismissRequest
    }
}
