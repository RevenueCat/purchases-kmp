package io.shortway.kobankat.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.shortway.kobankat.Offering
import io.shortway.kobankat.ui.revenuecatui.Paywall
import io.shortway.kobankat.ui.revenuecatui.PaywallOptions

@Composable
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var paywallOffering: Offering? by remember { mutableStateOf(null) }

            if (paywallOffering == null) MainScreen(
                onShowPaywallClick = { paywallOffering = it },
                modifier = Modifier.fillMaxSize()
            ) else Paywall(
                PaywallOptions(dismissRequest = { paywallOffering = null }) {
                    offering = paywallOffering
                }
            )

        }
    }
}
