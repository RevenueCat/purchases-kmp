/*
 * Created by Antonio Pallares on 3/10/26.
 * Copyright (c) 2026 RevenueCat, Inc. All rights reserved.
 */

package com.revenuecat.sdkupdatetester

import androidx.compose.ui.window.ComposeUIViewController
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration

@Suppress("unused", "FunctionName")
fun MainViewController(appUserIdToLogIn: String?): platform.UIKit.UIViewController {
    Purchases.logLevel = LogLevel.VERBOSE
    Purchases.configure(PurchasesConfiguration(BuildKonfig.apiKey))
    return ComposeUIViewController { App(appUserIdToLogIn) }
}
