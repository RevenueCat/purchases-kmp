package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.models.PurchasingData
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal

@Composable
internal fun PurchasingDataRow(
    data: PurchasingData,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = { Text(text = "purchasingData") },
        expandedContent = {
            Column(modifier = Modifier.padding(start = DefaultPaddingHorizontal)) {
                Text(text = "productId: ${data.productId}")
                Text(text = "type: ${data.productType}")
            }
        },
    )
}
