package com.revenuecat.purchases.kmp.installationtest

import androidx.compose.ui.window.ComposeUIViewController
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration

private const val PLACEHOLDER_API_KEY = "installation_test_api_key"

@Suppress("unused", "FunctionName")
fun MainViewController(): platform.UIKit.UIViewController {
    Purchases.configure(PurchasesConfiguration(PLACEHOLDER_API_KEY))
    return ComposeUIViewController { App() }
}
