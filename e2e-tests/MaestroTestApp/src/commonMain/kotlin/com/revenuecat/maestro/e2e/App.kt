package com.revenuecat.maestro.e2e

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.MaterialTheme

const val API_KEY = "MAESTRO_TESTS_REVENUECAT_API_KEY"

@Composable
fun App(initialTestFlow: String? = null) {
    var currentFlow by remember { mutableStateOf(initialTestFlow) }

    MaterialTheme {
        val testCase = currentFlow?.let { TEST_CASES[it] }
        if (testCase != null) {
            testCase.screen { currentFlow = null }
        } else {
            TestCasesScreen(onNavigate = { key -> currentFlow = key })
        }
    }
}
