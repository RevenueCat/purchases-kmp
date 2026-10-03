/*
 * Created by Antonio Pallares on 3/10/26.
 * Copyright (c) 2026 RevenueCat, Inc. All rights reserved.
 */

@file:OptIn(ExperimentalComposeUiApi::class)

package com.revenuecat.sdkupdatetester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Purchases.logLevel = LogLevel.VERBOSE
        if (!Purchases.isConfigured) {
            Purchases.configure(PurchasesConfiguration(BuildKonfig.apiKey))
        }
        val appUserIdToLogIn = intent.getStringExtra("app_user_id_to_log_in")?.takeIf { it.isNotEmpty() }
        setContent {
            App(
                appUserIdToLogIn,
                modifier = Modifier.safeDrawingPadding().semantics { testTagsAsResourceId = true },
            )
        }
    }
}
