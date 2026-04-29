package com.revenuecat.maestro.e2e

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions

@Composable
fun PurchaseThroughPaywallScreen(onBack: () -> Unit) {
    var hasPro by remember { mutableStateOf(false) }
    var showPaywall by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun fetchCustomerInfo() {
        Purchases.sharedInstance.getCustomerInfo(
            onError = { error -> errorMessage = error.message },
            onSuccess = { info ->
                hasPro = info.entitlements.active.containsKey("pro")
            }
        )
    }

    LaunchedEffect(showPaywall) {
        if (!showPaywall) {
            fetchCustomerInfo()
        }
    }

    if (showPaywall) {
        val options = remember {
            PaywallOptions(dismissRequest = { showPaywall = false }) {
                shouldDisplayDismissButton = true
            }
        }
        Paywall(options)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = if (hasPro) "Entitlements: pro" else "Entitlements: none",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            if (errorMessage != null) {
                Text(
                    text = "Error: $errorMessage",
                    fontSize = 14.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            Button(
                onClick = { showPaywall = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Present Paywall")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}
