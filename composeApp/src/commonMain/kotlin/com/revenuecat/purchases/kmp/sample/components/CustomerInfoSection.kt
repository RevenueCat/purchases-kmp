package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.CustomerInfo
import com.revenuecat.purchases.kmp.datetime.allExpirationInstants
import com.revenuecat.purchases.kmp.datetime.allPurchaseInstants
import com.revenuecat.purchases.kmp.datetime.firstSeenInstant
import com.revenuecat.purchases.kmp.datetime.latestExpirationInstant
import com.revenuecat.purchases.kmp.datetime.originalPurchaseInstant
import com.revenuecat.purchases.kmp.datetime.requestInstant
import com.revenuecat.purchases.kmp.sample.AsyncState
import com.revenuecat.purchases.kmp.sample.DefaultSpacingVertical

@Composable
internal fun CustomerInfoSection(
    state: AsyncState<CustomerInfo>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (state) {
            is AsyncState.Loading -> CircularProgressIndicator()
            is AsyncState.Error -> Text("Failed to get customer info")
            is AsyncState.Loaded -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
                ) {
                    Text(text = "originalAppUserId: ${state.value.originalAppUserId}")
                    Text(text = "originalApplicationVersion: ${state.value.originalApplicationVersion}")
                    Text(text = "firstSeenInstant: ${state.value.firstSeenInstant}")
                    Text(text = "latestExpirationInstant: ${state.value.latestExpirationInstant}")
                    Text(text = "originalPurchaseInstant: ${state.value.originalPurchaseInstant}")
                    Text(text = "requestInstant: ${state.value.requestInstant}")
                    Text(text = "managementUrlString: ${state.value.managementUrlString}")
                    CollapsibleStringsRow(
                        strings = state.value.activeSubscriptions,
                        label = "activeSubscriptions"
                    )
                    CollapsibleStringsRow(
                        strings = state.value.allPurchasedProductIdentifiers,
                        label = "allPurchasedProductIdentifiers"
                    )
                    CollapsibleMapRow(
                        map = state.value.allPurchaseInstants,
                        label = "allPurchaseInstants"
                    )
                    CollapsibleMapRow(
                        map = state.value.allExpirationInstants,
                        label = "allExpirationInstants"
                    )
                    EntitlementInfosRow(infos = state.value.entitlements)
                    TransactionsRow(
                        transactions = state.value.nonSubscriptionTransactions,
                        label = "nonSubscriptionTransactions"
                    )
                }
            }
        }
    }
}
