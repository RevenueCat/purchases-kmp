package com.revenuecat.purchases.kmp.installationtest

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions

@Composable
fun App() {
    MaterialTheme {
        Paywall(
            PaywallOptions(dismissRequest = {}) {},
        )
    }
}
