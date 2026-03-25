package com.revenuecat.maestro.e2e

import androidx.compose.ui.window.ComposeUIViewController
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration

@Suppress("unused", "FunctionName")
fun MainViewController() = run {
    Purchases.logLevel = LogLevel.DEBUG
    Purchases.configure(PurchasesConfiguration(API_KEY))
    ComposeUIViewController { App() }
}
