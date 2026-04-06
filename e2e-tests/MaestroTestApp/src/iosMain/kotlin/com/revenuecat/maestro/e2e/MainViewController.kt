package com.revenuecat.maestro.e2e

import androidx.compose.ui.window.ComposeUIViewController
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration
import platform.Foundation.NSLog

@OptIn(kotlin.experimental.ExperimentalNativeApi::class)
@Suppress("unused", "FunctionName")
fun MainViewController(): platform.UIKit.UIViewController {
    kotlin.native.setUnhandledExceptionHook { throwable ->
        NSLog("MaestroTestApp KOTLIN CRASH: %@", throwable.message ?: "no message")
        NSLog("MaestroTestApp KOTLIN CRASH stack:\n%@", throwable.stackTraceToString())
    }

    try {
        NSLog("MaestroTestApp: Configuring Purchases with API key length: %d", API_KEY.length.toLong())
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(PurchasesConfiguration(API_KEY))
        NSLog("MaestroTestApp: Purchases configured successfully")
    } catch (e: Exception) {
        NSLog("MaestroTestApp: Error configuring Purchases: %@", e.message ?: "unknown")
    }
    return ComposeUIViewController { App() }
}
