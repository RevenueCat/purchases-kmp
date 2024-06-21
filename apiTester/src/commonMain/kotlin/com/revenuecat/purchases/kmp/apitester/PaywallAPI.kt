package com.revenuecat.purchases.kmp.apitester

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallFooter
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions


@Suppress("unused", "UNUSED_ANONYMOUS_PARAMETER")
private class PaywallAPI {

    @Composable
    fun check(options: PaywallOptions) {
        Paywall(options = options)
    }

    @Composable
    fun checkFooter(options: PaywallOptions) {
        PaywallFooter(options = options)
        PaywallFooter(options = options) { paddingValues: PaddingValues ->

        }
    }
}
