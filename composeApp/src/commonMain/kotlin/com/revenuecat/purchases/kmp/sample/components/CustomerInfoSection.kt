package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.Storefront
import com.revenuecat.purchases.kmp.sample.AsyncState
import com.revenuecat.purchases.kmp.sample.DefaultSpacingVertical
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
internal fun CustomerInfoSection(
    state: AsyncState<CustomerInfo>,
    modifier: Modifier = Modifier
) {
    val storefront = produceState<Storefront?>(initialValue = null) {
        Purchases.sharedInstance.getStorefront { result ->
            value = result
        }
    }
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
                    Text(text = "firstSeen: ${state.value.firstSeen}")
                    Text(text = "latestExpirationDate: ${state.value.latestExpirationDate}")
                    Text(text = "originalPurchaseDate: ${state.value.originalPurchaseDate}")
                    Text(text = "requestDate: ${state.value.requestDate}")
                    Text(text = "managementUrlString: ${state.value.managementUrlString}")
                    Text(text = "storefrontCountryCode: ${storefront.value?.countryCode}")
                    CollapsibleStringsRow(
                        strings = state.value.activeSubscriptions,
                        label = "activeSubscriptions"
                    )
                    CollapsibleStringsRow(
                        strings = state.value.allPurchasedProductIdentifiers,
                        label = "allPurchasedProductIdentifiers"
                    )
                    CollapsibleMapRow(
                        map = state.value.allPurchaseDates,
                        label = "allPurchaseDates"
                    )
                    CollapsibleMapRow(
                        map = state.value.allExpirationDates,
                        label = "allExpirationDates"
                    )
                    EntitlementInfosRow(infos = state.value.entitlements)
                    SubscriptionInfoMapRow(
                        subscriptions = state.value.subscriptionsByProductIdentifier,
                        label = "subscriptionsByProductIdentifier"
                    )
                    TransactionsRow(
                        transactions = state.value.nonSubscriptionTransactions,
                        label = "nonSubscriptionTransactions"
                    )
                }
            }
        }
    }
}
