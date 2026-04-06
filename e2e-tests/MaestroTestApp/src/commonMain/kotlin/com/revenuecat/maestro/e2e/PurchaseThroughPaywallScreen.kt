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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions

@Composable
fun PurchaseThroughPaywallScreen(onBack: () -> Unit) {
    var hasPro by remember { mutableStateOf(false) }
    var showPaywall by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        logDebug("PurchaseThroughPaywallScreen entered composition")
        Purchases.sharedInstance.getCustomerInfo(
            onError = { error ->
                logDebug("Failed to get customer info: ${error.message}")
                errorMessage = error.message
            },
            onSuccess = { info ->
                logDebug("Got customer info, pro=${info.entitlements.active.containsKey("pro")}")
                hasPro = info.entitlements.active.containsKey("pro")
            }
        )
        logDebug("Pre-fetching offerings...")
        Purchases.sharedInstance.getOfferings(
            onError = { error ->
                logDebug("Failed to pre-fetch offerings: ${error.message}")
            },
            onSuccess = { offerings ->
                logDebug("Offerings pre-fetched, current=${offerings.current?.identifier}")
            }
        )
    }

    DisposableEffect(Unit) {
        Purchases.sharedInstance.delegate = object : com.revenuecat.purchases.kmp.PurchasesDelegate {
            override fun onPurchasePromoProduct(
                product: com.revenuecat.purchases.kmp.models.StoreProduct,
                startPurchase: (
                    onError: (error: com.revenuecat.purchases.kmp.models.PurchasesError, userCancelled: Boolean) -> Unit,
                    onSuccess: (storeTransaction: com.revenuecat.purchases.kmp.models.StoreTransaction, customerInfo: CustomerInfo) -> Unit
                ) -> Unit
            ) { /* no-op */ }

            override fun onCustomerInfoUpdated(customerInfo: CustomerInfo) {
                hasPro = customerInfo.entitlements.active.containsKey("pro")
            }
        }
        onDispose {
            Purchases.sharedInstance.delegate = null
        }
    }

    if (showPaywall) {
        logDebug("showPaywall=true, creating PaywallOptions")
        val options = remember {
            PaywallOptions(dismissRequest = {
                logDebug("Paywall dismiss requested")
                showPaywall = false
            }) {
                shouldDisplayDismissButton = true
            }
        }
        logDebug("Rendering Paywall composable")
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
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .testTag("entitlements-label")
            )
            if (errorMessage != null) {
                Text(
                    text = "Error: $errorMessage",
                    fontSize = 14.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .testTag("error-message")
                )
            }
            Button(
                onClick = {
                    logDebug("Present Paywall button tapped")
                    showPaywall = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("present-paywall-button")
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
