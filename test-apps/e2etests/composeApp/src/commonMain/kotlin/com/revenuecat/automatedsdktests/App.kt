package com.revenuecat.automatedsdktests

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.revenuecat.purchases.kmp.configure
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration
import com.revenuecat.purchases.kmp.ktx.awaitOfferings
import com.revenuecat.purchases.kmp.models.Offering
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions

@Composable
@Preview
fun App(offeringId: String? = null) {
    LaunchedEffect(Unit) {
        if (!Purchases.isConfigured) {
            Purchases.configure(BuildKonfig.apiKey) {
                appUserId = BuildKonfig.appUserId
            }
        }
    }

    MaterialTheme {
        if (offeringId != null) {
            PaywallScreen(offeringId)
        }
    }
}

@Composable
private fun PaywallScreen(offeringId: String) {
    var offering by remember { mutableStateOf<Offering?>(null) }

    LaunchedEffect(offeringId) {
        val offerings = Purchases.sharedInstance.awaitOfferings()
        offering = offerings[offeringId]
    }

    val currentOffering = offering
    if (currentOffering != null) {
        Paywall(PaywallOptions(dismissRequest = {}) {
            this.offering = currentOffering
            shouldDisplayDismissButton = false
        })
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }
}
