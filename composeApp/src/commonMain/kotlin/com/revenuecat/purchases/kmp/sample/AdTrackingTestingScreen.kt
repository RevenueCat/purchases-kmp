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
import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.AdDisplayedData
import com.revenuecat.purchases.kmp.models.AdFailedToLoadData
import com.revenuecat.purchases.kmp.models.AdFormat
import com.revenuecat.purchases.kmp.models.AdLoadedData
import com.revenuecat.purchases.kmp.models.AdMediatorName
import com.revenuecat.purchases.kmp.models.AdOpenedData
import com.revenuecat.purchases.kmp.models.AdRevenueData
import com.revenuecat.purchases.kmp.models.AdRevenuePrecision
import kotlinx.datetime.Clock

enum class AdTrackingFunction(val displayName: String) {
    TRACK_AD_DISPLAYED("trackAdDisplayed()"),
    TRACK_AD_OPENED("trackAdOpened()"),
    TRACK_AD_REVENUE("trackAdRevenue()"),
    TRACK_AD_LOADED("trackAdLoaded()"),
    TRACK_AD_FAILED_TO_LOAD("trackAdFailedToLoad()")
}

@OptIn(ExperimentalRevenueCatApi::class)
@Composable
fun AdTrackingTestingScreen(
    navigateTo: (Screen) -> Unit
) {
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var lastTrackedFunction by remember { mutableStateOf<AdTrackingFunction?>(null) }
    var messageColor by remember { mutableStateOf(Color.Gray) }

    fun clearStatus() {
        statusMessage = null
        lastTrackedFunction = null
    }

    fun trackAdDisplayed() {
        val data = AdDisplayedData(
            networkName = "TestNetwork",
            mediatorName = AdMediatorName.AD_MOB,
            adFormat = AdFormat.BANNER,
            placement = "home_banner",
            adUnitId = "ca-app-pub-1234567890",
            impressionId = "test-impression-${Clock.System.now().toEpochMilliseconds()}"
        )
        Purchases.sharedInstance.adTracker.trackAdDisplayed(data)
        statusMessage = "Ad displayed event tracked successfully!"
        lastTrackedFunction = AdTrackingFunction.TRACK_AD_DISPLAYED
        messageColor = Color.Green
    }

    fun trackAdOpened() {
        val data = AdOpenedData(
            networkName = "TestNetwork",
            mediatorName = AdMediatorName.AD_MOB,
            adFormat = AdFormat.BANNER,
            placement = "home_banner",
            adUnitId = "ca-app-pub-1234567890",
            impressionId = "test-impression-${Clock.System.now().toEpochMilliseconds()}"
        )
        Purchases.sharedInstance.adTracker.trackAdOpened(data)
        statusMessage = "Ad opened event tracked successfully!"
        lastTrackedFunction = AdTrackingFunction.TRACK_AD_OPENED
        messageColor = Color.Green
    }

    fun trackAdRevenue() {
        val data = AdRevenueData(
            networkName = "TestNetwork",
            mediatorName = AdMediatorName.APP_LOVIN,
            adFormat = AdFormat.REWARDED,
            placement = "rewarded_video",
            adUnitId = "ca-app-pub-1234567890",
            impressionId = "test-impression-${Clock.System.now().toEpochMilliseconds()}",
            revenueMicros = 5000000L, // $5.00 in micros
            currency = "USD",
            precision = AdRevenuePrecision.EXACT
        )
        Purchases.sharedInstance.adTracker.trackAdRevenue(data)
        statusMessage = "Ad revenue event tracked successfully!\nRevenue: $5.00 USD (5,000,000 micros)\nPrecision: EXACT"
        lastTrackedFunction = AdTrackingFunction.TRACK_AD_REVENUE
        messageColor = Color.Green
    }

    fun trackAdLoaded() {
        val data = AdLoadedData(
            networkName = "TestNetwork",
            mediatorName = AdMediatorName.AD_MOB,
            adFormat = AdFormat.INTERSTITIAL,
            placement = "interstitial",
            adUnitId = "ca-app-pub-1234567890",
            impressionId = "test-impression-${Clock.System.now().toEpochMilliseconds()}"
        )
        Purchases.sharedInstance.adTracker.trackAdLoaded(data)
        statusMessage = "Ad loaded event tracked successfully!"
        lastTrackedFunction = AdTrackingFunction.TRACK_AD_LOADED
        messageColor = Color.Green
    }

    fun trackAdFailedToLoad() {
        val data = AdFailedToLoadData(
            mediatorName = AdMediatorName.APP_LOVIN,
            adFormat = AdFormat.BANNER,
            placement = "banner",
            adUnitId = "ca-app-pub-1234567890",
            mediatorErrorCode = 404
        )
        Purchases.sharedInstance.adTracker.trackAdFailedToLoad(data)
        statusMessage = "Ad failed to load event tracked successfully!\nError code: 404"
        lastTrackedFunction = AdTrackingFunction.TRACK_AD_FAILED_TO_LOAD
        messageColor = Color.Green
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Ad Tracking Testing",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Test all ad tracking events with sample data",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Ad Displayed
        Button(
            onClick = { trackAdDisplayed() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Track Ad Displayed")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Ad Opened
        Button(
            onClick = { trackAdOpened() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Track Ad Opened")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Ad Revenue
        Button(
            onClick = { trackAdRevenue() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Track Ad Revenue ($5.00)")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Ad Loaded
        Button(
            onClick = { trackAdLoaded() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Track Ad Loaded")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Ad Failed to Load
        Button(
            onClick = { trackAdFailedToLoad() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Track Ad Failed to Load")
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
                Column {
                    Text(
                        text = message,
                        color = messageColor.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.body2
                    )
                    lastTrackedFunction?.let { function ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Function: ${function.displayName}",
                            fontFamily = FontFamily.Monospace,
                            color = Color.Gray,
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { clearStatus() },
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
                    text = "• Each button tracks a different ad event type\n" +
                            "• Events use sample data with test values\n" +
                            "• Check the RevenueCat dashboard to verify events\n" +
                            "• Ad tracking is an experimental API",
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
