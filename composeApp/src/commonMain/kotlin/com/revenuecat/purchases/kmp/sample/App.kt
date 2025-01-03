package com.revenuecat.purchases.kmp.sample

import WinBackTestingScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.RedeemWebPurchaseListener
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.OriginalTemplatePaywallFooter
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions
import dev.theolm.rinku.DeepLink
import dev.theolm.rinku.compose.ext.DeepLinkListener

@Composable
fun App() {
    MaterialTheme {
        ProcessDeepLinks()
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val loggingListener = rememberLoggingPaywallListener()
            var screen by remember { mutableStateOf<Screen>(Screen.Main) }
            val navigateTo = { destination: Screen -> screen = destination }

            when (val currentScreen = screen) {
                is Screen.Main -> MainScreen(
                    navigateTo = navigateTo,
                    modifier = Modifier.fillMaxSize()
                )

                is Screen.Paywall -> {
                    val options = PaywallOptions(dismissRequest = { navigateTo(Screen.Main) }) {
                        offering = currentScreen.offering
                        shouldDisplayDismissButton = true
                        listener = loggingListener
                    }
                    Paywall(options)
                }

                is Screen.PaywallFooter -> {
                    val options = PaywallOptions(dismissRequest = { navigateTo(Screen.Main) }) {
                        offering = currentScreen.offering
                        shouldDisplayDismissButton = true
                        listener = loggingListener
                    }
                    OriginalTemplatePaywallFooter(options) { contentPadding ->
                        CustomPaywallContent(
                            onBackClick = { navigateTo(Screen.Main) },
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Magenta)
                                .padding(contentPadding),
                        )
                    }
                }

                is Screen.WinBackTesting -> WinBackTestingScreen(
                    navigateTo = navigateTo
                )
            }
        }
    }
}

@Composable
private fun CustomPaywallContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Custom paywall content!",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4,
        )
        Button(onClick = onBackClick) {
            Text("Go back")
        }
    }
}

@Composable
private fun ProcessDeepLinks() {
    var alertMessage by remember { mutableStateOf<String?>(null) }

    DeepLinkListener {
        val webPurchaseRedemption = Purchases.parseAsWebPurchaseRedemption(it.data)
        if (webPurchaseRedemption != null && Purchases.isConfigured) {
            Purchases.sharedInstance.redeemWebPurchase(webPurchaseRedemption) { result ->
                alertMessage = when (result) {
                    is RedeemWebPurchaseListener.Result.Error ->
                        "Error redeeming web purchase: ${result.error.message}"
                    is RedeemWebPurchaseListener.Result.Expired ->
                        "Web purchase redemption token expired. Email sent to: ${result.obfuscatedEmail}"
                    RedeemWebPurchaseListener.Result.InvalidToken ->
                        "Invalid web purchase redemption token"
                    RedeemWebPurchaseListener.Result.PurchaseBelongsToOtherUser ->
                        "Web purchase belongs to another user"
                    is RedeemWebPurchaseListener.Result.Success ->
                        "Web purchase redeemed successfully. Entitlements: ${result.customerInfo.entitlements.active}"
                }
            }
        }
    }

    alertMessage?.let {
        AlertDialog(
            onDismissRequest = { alertMessage = null },
            title = { Text("Web Purchase Redemption result") },
            text = { Text(it) },
            buttons = {
                Row(
                    modifier = Modifier. padding(all = 8.dp),
                    horizontalArrangement = Arrangement. Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { alertMessage = null }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }
}
