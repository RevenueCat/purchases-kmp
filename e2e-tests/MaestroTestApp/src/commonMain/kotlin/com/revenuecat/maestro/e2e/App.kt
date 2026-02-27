package com.revenuecat.maestro.e2e

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.MaterialTheme

const val API_KEY = "MAESTRO_TESTS_REVENUECAT_API_KEY"

enum class Screen { TestCases, PurchaseThroughPaywall }

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.TestCases) }

    MaterialTheme {
        when (currentScreen) {
            Screen.TestCases -> TestCasesScreen(
                onNavigate = { currentScreen = Screen.PurchaseThroughPaywall }
            )
            Screen.PurchaseThroughPaywall -> PurchaseThroughPaywallScreen(
                onBack = { currentScreen = Screen.TestCases }
            )
        }
    }
}
