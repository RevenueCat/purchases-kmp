package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.models.Price
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal
import com.revenuecat.purchases.kmp.sample.DefaultSpacingVertical

@Composable
internal fun PriceRow(
    price: Price?,
    modifier: Modifier = Modifier,
    label: String = "price",
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = {
            Text(text = "$label: ${price?.formatted}")
        },
        expandedContent = {
            Column(
                modifier = Modifier.padding(start = DefaultPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
            ) {
                Text(text = "currencyCode: ${price?.currencyCode}")
                Text(text = "amountMicros: ${price?.amountMicros}")
            }
        },
    )
}
