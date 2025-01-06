package com.revenuecat.purchases.kmp.sample

import WinBackTestingScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
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
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallFooter
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions

@Composable
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val loggingListener = rememberLoggingPaywallListener()
            var screen by remember { mutableStateOf<Screen>(Screen.Main) }

            when (val currentScreen = screen) {
                is Screen.Main -> MainScreen(
                    onShowPaywallClick = { offering, footer ->
                        screen =
                            if (footer) Screen.PaywallFooter(offering)
                            else Screen.Paywall(offering)
                    },
                    setScreen = { screen = it },
                    modifier = Modifier.fillMaxSize()
                )

                is Screen.Paywall -> {
                    val options = PaywallOptions(dismissRequest = { screen = Screen.Main }) {
                        offering = currentScreen.offering
                        shouldDisplayDismissButton = true
                        listener = loggingListener
                    }
                    Paywall(options)
                }

                is Screen.PaywallFooter -> {
                    val options = PaywallOptions(dismissRequest = { screen = Screen.Main }) {
                        offering = currentScreen.offering
                        shouldDisplayDismissButton = true
                        listener = loggingListener
                    }
                    PaywallFooter(options) { contentPadding ->
                        CustomPaywallContent(
                            onBackClick = { screen = Screen.Main },
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Magenta)
                                .padding(contentPadding),
                        )
                    }
                }

                is Screen.WinBackTesting -> WinBackTestingScreen()
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
