/*
 * Created by Antonio Pallares on 3/10/26.
 * Copyright (c) 2026 RevenueCat, Inc. All rights reserved.
 */

package com.revenuecat.sdkupdatetester

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun App(appUserIdToLogIn: String?, modifier: Modifier = Modifier) {
    MaterialTheme {
        var showPurchaseScreen by remember { mutableStateOf(false) }
        Surface(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
            ) {
                Text(
                    text = if (showPurchaseScreen) "Purchase" else "SDK Update Tester",
                    style = MaterialTheme.typography.h5,
                )
                if (showPurchaseScreen) {
                    PurchaseScreen()
                    OutlinedButton(onClick = { showPurchaseScreen = false }) { Text("Back") }
                } else {
                    HomeScreen(appUserIdToLogIn, onPurchaseScreen = { showPurchaseScreen = true })
                }
            }
        }
    }
}
