package com.revenuecat.maestro.e2e

import androidx.compose.ui.window.ComposeUIViewController
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration
import platform.Foundation.NSLog

@Suppress("unused", "FunctionName")
fun MainViewController(): platform.UIKit.UIViewController {
    try {
        NSLog("MaestroTestApp: Configuring Purchases with API key length: ${API_KEY.length}")
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(PurchasesConfiguration(API_KEY))
        NSLog("MaestroTestApp: Purchases configured successfully")
    } catch (e: Exception) {
        NSLog("MaestroTestApp: Error configuring Purchases: ${e.message}")
    }
    return ComposeUIViewController { App() }
}
