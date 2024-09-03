package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.models.Price
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal

@Composable
internal fun PriceRow(
    price: Price,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = {
            Text(text = "price: ${price.formatted}")
        },
        expandedContent = {
            Column(modifier = Modifier.padding(start = DefaultPaddingHorizontal)) {
                Text(text = "currencyCode: ${price.currencyCode}")
                Text(text = "amountMicros: ${price.amountMicros}")
            }
        },
    )
}
