/*
 * Created by Antonio Pallares on 3/10/26.
 * Copyright (c) 2026 RevenueCat, Inc. All rights reserved.
 */

package com.revenuecat.sdkupdatetester

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.frameworkVersion
import com.revenuecat.purchases.kmp.ktx.awaitLogIn
import com.revenuecat.purchases.kmp.models.PurchasesException
import kotlinx.coroutines.launch

@Composable
internal fun HomeScreen(appUserIdToLogIn: String?, onPurchaseScreen: () -> Unit) {
    var appUserId by remember { mutableStateOf(Purchases.sharedInstance.appUserID) }
    var isLoggingIn by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
    ) {
        Text(
            text = "RevenueCat SDK ${Purchases.frameworkVersion}",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.testTag("sdk_version"),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("App User ID", style = MaterialTheme.typography.subtitle1)
            Text(
                text = appUserId,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp).testTag("app_user_id"),
            )
        }
        Button(
            onClick = {
                appUserIdToLogIn?.let { userId ->
                    isLoggingIn = true
                    errorMessage = null
                    scope.launch {
                        try {
                            Purchases.sharedInstance.awaitLogIn(userId)
                        } catch (e: PurchasesException) {
                            errorMessage = "Log in failed: ${e.message}"
                        } finally {
                            appUserId = Purchases.sharedInstance.appUserID
                            isLoggingIn = false
                        }
                    }
                }
            },
            enabled = appUserIdToLogIn != null && !isLoggingIn,
            modifier = Modifier.testTag("log_in_button"),
        ) { Text("Log in") }
        OutlinedButton(onClick = onPurchaseScreen, modifier = Modifier.testTag("purchase_screen_button")) {
            Text("Go to purchase screen")
        }
        errorMessage?.let {
            Text(it, color = MaterialTheme.colors.error, modifier = Modifier.testTag("error_message"))
        }
    }
}
