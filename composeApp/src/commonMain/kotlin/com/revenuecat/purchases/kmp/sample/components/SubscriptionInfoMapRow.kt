package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.models.SubscriptionInfo
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal
import com.revenuecat.purchases.kmp.sample.DefaultSpacingVertical
import kotlin.time.ExperimentalTime

@Composable
internal fun SubscriptionInfoMapRow(
    subscriptions: Map<String, SubscriptionInfo>,
    label: String,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        collapsedContent = { Text(text = "$label: ${subscriptions.size}") },
        expandedContent = {
            Column(
                modifier = Modifier.padding(start = DefaultPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
            ) {
                subscriptions.forEach { (_, info) -> SubscriptionInfoRow(info) }
            }
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalTime::class)
@Composable
internal fun SubscriptionInfoRow(
    info: SubscriptionInfo,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        collapsedContent = { Text(text = info.productIdentifier) },
        expandedContent = {
            Column(
                modifier = Modifier.padding(start = DefaultPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
            ) {
                Text(text = "isActive: ${info.isActive}")
                Text(text = "willRenew: ${info.willRenew}")
                Text(text = "purchaseDate: ${info.purchaseDate}")
                Text(text = "originalPurchaseDate: ${info.originalPurchaseDate}")
                Text(text = "expiresDate: ${info.expiresDate}")
                Text(text = "store: ${info.store}")
                Text(text = "isSandbox: ${info.isSandbox}")
                Text(text = "ownershipType: ${info.ownershipType}")
                Text(text = "periodType: ${info.periodType}")
                Text(text = "storeTransactionId: ${info.storeTransactionId}")
                Text(text = "productPlanIdentifier: ${info.productPlanIdentifier}")
                Text(text = "price: ${info.price?.formatted}")
                Text(text = "managementUrlString: ${info.managementUrlString}")
                Text(text = "unsubscribeDetectedAt: ${info.unsubscribeDetectedAt}")
                Text(text = "billingIssuesDetectedAt: ${info.billingIssuesDetectedAt}")
                Text(text = "gracePeriodExpiresDate: ${info.gracePeriodExpiresDate}")
                Text(text = "refundedAt: ${info.refundedAt}")
                Text(text = "autoResumeDate: ${info.autoResumeDate}")
            }
        },
        modifier = modifier,
    )
}

