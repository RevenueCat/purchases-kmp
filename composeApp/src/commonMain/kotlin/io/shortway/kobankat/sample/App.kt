package io.shortway.kobankat.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
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
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "KobanKat logs",
                modifier = Modifier.padding(all = 16.dp),
                style = MaterialTheme.typography.h4
            )

            LogView(
                logs = logs,
                modifier = Modifier
                    .weight(1f)
                    .background(Color.LightGray),
            )
        }
    }
}

@Composable
private fun LogView(
    logs: SnapshotStateList<String>,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    LaunchedEffect(logs.size) {
        listState.animateScrollToItem(index = logs.lastIndex.coerceAtLeast(0))
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(all = 16.dp)
    ) {
        items(logs) {
            Text(
                text = it,
                modifier = Modifier.padding(vertical = 4.dp),
                fontFamily = FontFamily.Monospace,
                style = MaterialTheme.typography.caption
            )
        }
    }
}
