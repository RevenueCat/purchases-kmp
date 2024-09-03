package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal

@Composable
internal fun StoreProductDiscountsRow(
    discounts: List<StoreProductDiscount>,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = { Text(text = "discounts: ${discounts.size}") },
        expandedContent = {
            Column(modifier = Modifier.padding(start = DefaultPaddingHorizontal)) {
                discounts.forEach { discount -> StoreProductDiscountRow(discount = discount) }
            }
        },
    )
}

@Composable
internal fun StoreProductDiscountRow(
    discount: StoreProductDiscount,
    modifier: Modifier = Modifier,
    label: String = "discount",
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = {
            Text(
                text = "$label: ${discount.price.formatted} per ${discount.subscriptionPeriod.value} ${discount.subscriptionPeriod.unit}"
            )
        },
        expandedContent = {
            Column(modifier = Modifier.padding(start = DefaultPaddingHorizontal)) {
                Text(text = "offerIdentifier: ${discount.offerIdentifier}")
                Text(text = "numberOfPeriods: ${discount.numberOfPeriods}")
                Text(text = "type: ${discount.type}")
                Text(text = "paymentMode: ${discount.paymentMode}")
                PriceRow(price = discount.price)
                PeriodText(period = discount.subscriptionPeriod, label = "subscriptionPeriod")
            }
        },
    )
}
