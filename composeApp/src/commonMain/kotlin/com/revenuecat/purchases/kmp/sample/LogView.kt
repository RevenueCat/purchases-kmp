package com.revenuecat.purchases.kmp.sample

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
internal fun LogView(
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
