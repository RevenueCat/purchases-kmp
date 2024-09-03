package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal

@Composable
internal fun CollapsibleRow(
    collapsedContent: @Composable RowScope.() -> Unit,
    expandedContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        var expanded by remember { mutableStateOf(false) }
        val iconRotation by animateFloatAsState(if (expanded) 180f else 0f)

        Row(
            modifier = Modifier.clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            collapsedContent()
            Spacer(modifier = Modifier.weight(1f, fill = true))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.rotate(iconRotation)
            )
        }

        AnimatedVisibility(visible = expanded) {
            expandedContent()
        }
    }
}

@Composable
internal fun CollapsibleStringsRow(
    strings: List<String>,
    label: String,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = { Text(text = "$label: ${strings.size}") },
        expandedContent = {
            Column(modifier = Modifier.padding(start = DefaultPaddingHorizontal)) {
                strings.forEach { string -> Text(text = string) }
            }
        },
    )
}

@Composable
internal fun <K, V> CollapsibleMapRow(
    map: Map<K, V>,
    label: String,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = { Text(text = "$label: ${map.size}") },
        expandedContent = {
            Column(modifier = Modifier.padding(start = DefaultPaddingHorizontal)) {
                map.forEach { (key, value) -> Text(text = "$key: $value") }
            }
        },
    )
}
