package io.shortway.kobankat.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.shortway.kobankat.LogLevel
import io.shortway.kobankat.PurchasesConfiguration
import io.shortway.kobankat.PurchasesFactory

@Composable
fun App() {
    val logs = mutableStateListOf<String>()
    LaunchedEffect(Unit) {
        // In a real app, you'd probably have a class dedicated to app initialization logic.
        PurchasesFactory.configure(
            PurchasesConfiguration(
                apiKey = "YOUR-REVENUECAT-API-KEY",
            )
        )
        PurchasesFactory.logLevel = LogLevel.VERBOSE
        PurchasesFactory.logHandler = SimpleLogHandler { logs.add(it) }
    }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn {
                items(logs) {
                    Text(text = it)
                }
            }
        }
    }
}