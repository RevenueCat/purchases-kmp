package com.revenuecat.purchases.kmp.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import arrow.core.Either
import com.revenuecat.purchases.kmp.Offering
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration
import com.revenuecat.purchases.kmp.either.awaitOfferingsEither
import com.revenuecat.purchases.kmp.sample.components.OfferingsSection
import com.revenuecat.purchases.kmp.sample.components.OfferingsState

@Composable
fun MainScreen(
    onShowPaywallClick: (offering: Offering?, footer: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .padding(all = 16.dp)
    ) {
        Text(
            text = "Configuration",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6,
        )

        var isConfigured by remember { mutableStateOf(Purchases.isConfigured) }
        var configuration by remember { mutableStateOf(Configuration(apiKey = "", userId = "")) }
        ConfigurationSettings(
            configuration = configuration,
            onConfigurationChanged = { configuration = it },
            modifier = Modifier.fillMaxWidth()
        )

        if (!isConfigured) {
            Spacer(modifier = Modifier.size(8.dp))
            Button(
                onClick = {
                    Purchases.configure(configuration.toPurchasesConfiguration())
                    isConfigured = Purchases.isConfigured
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = configuration.userId.isNotBlank() && configuration.apiKey.isNotBlank(),
            ) {
                Text("Configure")
            }
        } else {
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "Offerings",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6,
            )

            var offeringsState: OfferingsState by remember { mutableStateOf(OfferingsState.Loading) }
            LaunchedEffect(Unit) {
                offeringsState =
                    when (val offerings = Purchases.sharedInstance.awaitOfferingsEither()) {
                        is Either.Left -> OfferingsState.Error
                        is Either.Right -> OfferingsState.Loaded(offerings.value)
                    }
            }

            OfferingsSection(
                state = offeringsState,
                onShowPaywallClick = onShowPaywallClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun ConfigurationSettings(
    configuration: Configuration,
    onConfigurationChanged: (Configuration) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = "API key",
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = configuration.apiKey,
            onValueChange = { onConfigurationChanged(configuration.copy(apiKey = it)) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace)
        )
        Text(
            text = "user ID",
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = configuration.userId,
            onValueChange = { onConfigurationChanged(configuration.copy(userId = it)) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace)
        )
    }
}

private data class Configuration(val apiKey: String, val userId: String) {
    fun toPurchasesConfiguration(builder: PurchasesConfiguration.Builder.() -> Unit = { }) =
        PurchasesConfiguration(apiKey) {
            appUserId = userId
            apply(builder)
        }
}
