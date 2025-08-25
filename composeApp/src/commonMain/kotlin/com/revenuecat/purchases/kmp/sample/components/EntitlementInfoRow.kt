@file:OptIn(ExperimentalTime::class)

package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.datetime.billingIssueDetectedAtInstant
import com.revenuecat.purchases.kmp.datetime.expirationInstant
import com.revenuecat.purchases.kmp.datetime.latestPurchaseInstant
import com.revenuecat.purchases.kmp.datetime.originalPurchaseInstant
import com.revenuecat.purchases.kmp.datetime.unsubscribeDetectedAtInstant
import com.revenuecat.purchases.kmp.models.EntitlementInfo
import com.revenuecat.purchases.kmp.models.EntitlementInfos
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal
import com.revenuecat.purchases.kmp.sample.DefaultSpacingVertical
import kotlin.time.ExperimentalTime

@Composable
internal fun EntitlementInfosRow(
    infos: EntitlementInfos,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        collapsedContent = {
            Text(text = "entitlements: ${infos.all.size}")
        },
        expandedContent = {
            Column(
                modifier = Modifier.padding(start = DefaultPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
            ) {
                Text(text = "verification: ${infos.verification}")
                infos.all.forEach { (_, info) ->
                    EntitlementInfoRow(info = info)
                }
            }
        },
        modifier = modifier,
    )
}

@Composable
internal fun EntitlementInfoRow(
    info: EntitlementInfo,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = { Text(text = info.identifier) },
        expandedContent = {
            Column(
                modifier = Modifier.padding(start = DefaultPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
            ) {
                Text(text = "isActive: ${info.isActive}")
                Text(text = "willRenew: ${info.willRenew}")
                Text(text = "latestPurchaseInstant: ${info.latestPurchaseInstant}")
                Text(text = "originalPurchaseInstant: ${info.originalPurchaseInstant}")
                Text(text = "isSandbox: ${info.isSandbox}")
                Text(text = "store: ${info.store}")
                Text(text = "verification: ${info.verification}")
                Text(text = "ownershipType: ${info.ownershipType}")
                Text(text = "periodType: ${info.periodType}")
                Text(text = "productIdentifier: ${info.productIdentifier}")
                Text(text = "productPlanIdentifier: ${info.productPlanIdentifier}")
                Text(text = "expirationInstant: ${info.expirationInstant}")
                Text(text = "unsubscribeDetectedAtInstant: ${info.unsubscribeDetectedAtInstant}")
                Text(text = "billingIssueDetectedAtInstant: ${info.billingIssueDetectedAtInstant}")
            }
        },
    )
}
