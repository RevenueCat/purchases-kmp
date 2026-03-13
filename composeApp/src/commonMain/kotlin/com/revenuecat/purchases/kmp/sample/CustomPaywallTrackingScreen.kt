package com.revenuecat.purchases.kmp.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.revenuecat.purchases.kmp.Purchases

@Composable
fun CustomPaywallTrackingScreen(
    navigateTo: (Screen) -> Unit
) {
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var messageColor by remember { mutableStateOf(Color.Gray) }

    fun trackImpressionWithoutId() {
        Purchases.sharedInstance.trackCustomPaywallImpression()
        statusMessage = "Custom paywall impression tracked (no paywallId)"
        messageColor = Color.Green
    }

    fun trackImpressionWithId() {
        val testPaywallId = "test-custom-paywall-123"
        Purchases.sharedInstance.trackCustomPaywallImpression(paywallId = testPaywallId)
        statusMessage = "Custom paywall impression tracked with paywallId: $testPaywallId"
        messageColor = Color.Green
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Custom Paywall Tracking",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Track custom paywall impression events",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { trackImpressionWithoutId() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Track Impression (no paywallId)")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { trackImpressionWithId() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Track Impression (with paywallId)")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Status message display
        statusMessage?.let { message ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = messageColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = message,
                    color = messageColor.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    statusMessage = null
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear Status")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Info section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Blue.copy(alpha = 0.07f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Testing Tips:",
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Track impression without a paywallId or with one\n" +
                            "• Each call creates a separate impression event\n" +
                            "• Check the RevenueCat dashboard to verify events",
                    style = MaterialTheme.typography.body2,
                    color = Color.Black.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(
            onClick = { navigateTo(Screen.Main) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go back")
        }
    }
}
