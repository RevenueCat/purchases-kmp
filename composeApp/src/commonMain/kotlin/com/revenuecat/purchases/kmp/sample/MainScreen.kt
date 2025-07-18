package com.revenuecat.purchases.kmp.sample

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import arrow.core.Either
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration
import com.revenuecat.purchases.kmp.PurchasesDelegate
import com.revenuecat.purchases.kmp.either.awaitOfferingsEither
import com.revenuecat.purchases.kmp.ktx.awaitCustomerInfo
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.Offerings
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.sample.components.CustomerInfoSection
import com.revenuecat.purchases.kmp.sample.components.OfferingsSection
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

@Composable
fun MainScreen(
    navigateTo: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .padding(all = 16.dp)
    ) {
        var isConfigured by remember { mutableStateOf(Purchases.isConfigured) }
        var immediatelyShowPaywallFooterAfterConfiguring by remember { mutableStateOf(false) }

        AnimatedContent(targetState = isConfigured) { configured ->
            if (!configured) Column {
                Text(
                    text = "Configuration",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                )

                Spacer(modifier = Modifier.size(8.dp))

                var configuration by remember {
                    mutableStateOf(
                        Configuration(apiKey = BuildKonfig.apiKey, userId = BuildKonfig.appUserId)
                    )
                }
                ConfigurationSettings(
                    configuration = configuration,
                    onConfigurationChanged = { configuration = it },
                    modifier = Modifier.fillMaxWidth()
                )
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

                Spacer(modifier = Modifier.size(16.dp))

                ConfigurationTip(modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.size(16.dp))

                ImmediatelyShowFooter(
                    checked = immediatelyShowPaywallFooterAfterConfiguring,
                    onCheckedChange = { immediatelyShowPaywallFooterAfterConfiguring = it },
                )

            } else Column {
                LaunchedEffect(Unit) {
                    if (immediatelyShowPaywallFooterAfterConfiguring) {
                        navigateTo(Screen.PaywallFooter(offering = null))
                    }
                }
                var offeringsState: AsyncState<Offerings> by remember {
                    mutableStateOf(AsyncState.Loading)
                }
                LaunchedEffect(Unit) {
                    offeringsState =
                        when (val offerings = Purchases.sharedInstance.awaitOfferingsEither()) {
                            is Either.Left -> AsyncState.Error
                            is Either.Right -> AsyncState.Loaded(offerings.value)
                        }
                }
                val customerInfo by Purchases.sharedInstance.rememberCustomerInfoState()
                val customerInfoState by remember {
                    derivedStateOf {
                        customerInfo?.let { AsyncState.Loaded(it) } ?: AsyncState.Loading
                    }
                }

                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "CustomerInfo",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                )
                Spacer(modifier = Modifier.size(8.dp))
                CustomerInfoSection(
                    state = customerInfoState,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Offerings",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                )
                Spacer(modifier = Modifier.size(8.dp))
                OfferingsSection(
                    state = offeringsState,
                    navigateTo = navigateTo,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.size(16.dp))
                TextButton(
                    onClick = { navigateTo(Screen.WinBackTesting) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Win-Back Offer Testing")
                }

                Spacer(modifier = Modifier.size(16.dp))
                TextButton(
                    onClick = { navigateTo(Screen.CustomerCenter) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Customer Center")
                }
            }
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
        Spacer(modifier = Modifier.size(DefaultSpacingVertical))
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

@Composable
private fun ConfigurationTip(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(
                color = Color.Green.copy(alpha = 0.1f),
                shape = RoundedCornerShape(size = 16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = "Tip: you can configure these values in your local.properties file using the following keys:")
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = "revenuecat.apiKey.google", fontFamily = FontFamily.Monospace)
        Text(text = "revenuecat.apiKey.apple", fontFamily = FontFamily.Monospace)
        Text(text = "revenuecat.appUserId", fontFamily = FontFamily.Monospace)
    }
}

@Composable
private fun ImmediatelyShowFooter(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
) {
    Column(
        modifier = Modifier.background(
            color = Color.Blue.copy(alpha = 0.07f),
            shape = RoundedCornerShape(size = 16.dp)
        ).padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Immediately show paywall footer after configuring.",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.body1,
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "This is useful if you want to test size animations when the " +
                    "footer goes from the loading to loaded state.",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.caption,
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

private val Purchases.customerInfoFlow: Flow<CustomerInfo>
    get() = callbackFlow {
        delegate = object : PurchasesDelegate {
            override fun onPurchasePromoProduct(
                product: StoreProduct,
                startPurchase: (onError: (error: PurchasesError, userCancelled: Boolean) -> Unit, onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit) -> Unit
            ) {
                // Not implemented
            }

            override fun onCustomerInfoUpdated(customerInfo: CustomerInfo) {
                trySendBlocking(customerInfo)
            }
        }

        awaitClose { delegate = null }
    }.onStart { emit(awaitCustomerInfo()) }

@Composable
private fun Purchases.rememberCustomerInfoState(): State<CustomerInfo?> =
    remember { customerInfoFlow }.collectAsState(initial = null)
