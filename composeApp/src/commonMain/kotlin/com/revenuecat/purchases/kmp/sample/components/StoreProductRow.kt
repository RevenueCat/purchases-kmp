package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.ktx.awaitTrialOrIntroductoryPriceEligibility
import com.revenuecat.purchases.kmp.models.IntroEligibility
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal
import com.revenuecat.purchases.kmp.sample.DefaultSpacingVertical

@Composable
internal fun StoreProductRow(
    product: StoreProduct,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        collapsedContent = { Text(text = "storeProduct: ${product.id}") },
        expandedContent = {
            Column(
                modifier = Modifier.padding(start = DefaultPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
            ) {
                Text(text = "title: ${product.title}")
                Text(text = "description: ${product.localizedDescription}")
                Text(text = "type: ${product.type}")
                Text(text = "category: ${product.category}")
                product.period?.let { PeriodText(period = it) } ?: Text(text = "period: null")
                PriceRow(price = product.price)
                PurchasingDataRow(data = product.purchasingData)
                product.presentedOfferingContext?.let {
                    PresentedOfferingContextRow(context = it)
                } ?: Text(text = "presentedOfferingContext: null")
                product.defaultOption?.let {
                    SubscriptionOptionRow(option = it, label = "defaultOption")
                } ?: Text(text = "defaultOption: null")
                product.subscriptionOptions
                    ?.let { SubscriptionOptionsRow(options = it) }
                    ?: Text(text = "subscriptionOptions: null")
                product.introductoryDiscount?.let {
                    StoreProductDiscountRow(discount = it, label = "introductoryDiscount")
                } ?: Text(text = "introductoryDiscount: null")
                StoreProductDiscountsRow(discounts = product.discounts)

                val introEligibilityStatus by produceState<IntroEligibility.Status?>(null) {
                    value = Purchases.sharedInstance
                        .awaitTrialOrIntroductoryPriceEligibility(listOf(product))[product]
                        ?.status
                }
                Text(text = "introEligibilityStatus: ${introEligibilityStatus ?: "Loading..."}")
            }
        },
        modifier = modifier,
    )
}
