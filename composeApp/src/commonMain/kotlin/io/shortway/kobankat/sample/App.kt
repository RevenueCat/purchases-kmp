package io.shortway.kobankat.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import io.shortway.kobankat.Offering
import io.shortway.kobankat.ui.revenuecatui.Paywall
import io.shortway.kobankat.ui.revenuecatui.PaywallFooter
import io.shortway.kobankat.ui.revenuecatui.PaywallOptions

@Composable
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var showPaywallAsFooter by remember { mutableStateOf(false) }
            var paywallOffering: Offering? by remember { mutableStateOf(null) }

            if (paywallOffering == null) MainScreen(
                onShowPaywallClick = { offering, footer ->
                    showPaywallAsFooter = footer
                    paywallOffering = offering
                },
                modifier = Modifier.fillMaxSize()
            ) else {
                val options = PaywallOptions(dismissRequest = { paywallOffering = null }) {
                    offering = paywallOffering
                    shouldDisplayDismissButton = true
                }

                if (showPaywallAsFooter) PaywallFooter(options) { contentPadding ->
                    CustomPaywallContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Magenta)
                            .padding(contentPadding),
                    )
                }
                else Paywall(options)
            }

        }
    }
}

@Composable
private fun CustomPaywallContent(modifier: Modifier = Modifier) {
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
    }
}
