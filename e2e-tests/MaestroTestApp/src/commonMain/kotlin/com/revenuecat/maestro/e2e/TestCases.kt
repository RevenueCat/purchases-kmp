package com.revenuecat.maestro.e2e

import androidx.compose.runtime.Composable

// Single registration point for every E2E test case.
// To add a new test case:
//   1. Create a screen composable in this package (e.g. YourTestScreen.kt).
//   2. Add an entry to TEST_CASES below keyed by the Maestro `e2e_test_flow`
//      argument value.
// Mirrors the pattern used by purchases-capacitor (src/test_cases.ts) and
// purchases-flutter (lib/test_cases.dart).

data class TestCase(
    val title: String,
    val screen: @Composable (onBack: () -> Unit) -> Unit,
)

val TEST_CASES: Map<String, TestCase> = mapOf(
    "purchase_through_paywall" to TestCase(
        title = "Purchase through paywall",
        screen = { onBack -> PurchaseThroughPaywallScreen(onBack) },
    ),
)
