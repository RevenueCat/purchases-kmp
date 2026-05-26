package com.revenuecat.purchases.kmp.installationtest

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions

@Composable
fun App() {
    MaterialTheme {
        // Paywall pulls in RevenueCatUI static Swift artifacts for the link check (#859).
        Paywall(
            PaywallOptions(dismissRequest = {}) {},
        )
        Text("Installation test")
    }
}
