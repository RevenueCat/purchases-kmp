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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.CustomPaywallImpressionParams

@Composable
fun CustomPaywallTrackingScreen(
    navigateTo: (Screen) -> Unit
) {
    var paywallId by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var messageColor by remember { mutableStateOf(Color.Gray) }

    fun currentPaywallId(): String? {
        return paywallId.trim().ifEmpty { null }
    }

    fun trackImpressionWithoutOffering() {
        val trimmedPaywallId = currentPaywallId()

        if (trimmedPaywallId == null) {
            Purchases.sharedInstance.trackCustomPaywallImpression()
        } else {
            Purchases.sharedInstance.trackCustomPaywallImpression(
                CustomPaywallImpressionParams(paywallId = trimmedPaywallId)
            )
        }
        statusMessage = "Tracked without offering (paywallId: ${trimmedPaywallId ?: "nil"})"
        messageColor = Color.Green
    }

    fun trackImpressionWithCurrentOffering() {
        val trimmedPaywallId = paywallId.trim().ifEmpty { null }

        Purchases.sharedInstance.getOfferings(
            onError = { error ->
                statusMessage = "Failed to get offerings: $error"
                messageColor = Color.Red
            },
            onSuccess = { offerings ->
                val currentOffering = offerings.current
                if (currentOffering == null) {
                    statusMessage = "No current offering configured"
                    messageColor = Color.Red
                    return@getOfferings
                }

                Purchases.sharedInstance.trackCustomPaywallImpression(
                    CustomPaywallImpressionParams(
                        paywallId = trimmedPaywallId,
                        offering = currentOffering,
                    )
                )
                statusMessage =
                    "Tracked with offering: ${currentOffering.identifier} (paywallId: ${trimmedPaywallId ?: "nil"})"
                messageColor = Color.Green
            }
        )
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

        OutlinedTextField(
            value = paywallId,
            onValueChange = { paywallId = it },
            label = { Text("Paywall ID (optional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { trackImpressionWithoutOffering() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Track Custom Paywall Impression Without Offering")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { trackImpressionWithCurrentOffering() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Track Custom Paywall Impression With Current Offering")
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
