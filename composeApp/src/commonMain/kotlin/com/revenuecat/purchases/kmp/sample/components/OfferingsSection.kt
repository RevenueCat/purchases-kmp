package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.revenuecat.purchases.kmp.models.Offering
import com.revenuecat.purchases.kmp.models.Offerings
import com.revenuecat.purchases.kmp.ui.revenuecatui.CustomVariableValue
import com.revenuecat.purchases.kmp.sample.AsyncState
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal
import com.revenuecat.purchases.kmp.sample.DefaultSpacingVertical
import com.revenuecat.purchases.kmp.sample.Screen

@Composable
internal fun OfferingsSection(
    state: AsyncState<Offerings>,
    navigateTo: (Screen) -> Unit,
    customVariables: Map<String, CustomVariableValue> = emptyMap(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (state) {
            is AsyncState.Loading -> CircularProgressIndicator()
            is AsyncState.Error -> Text("Failed to get offerings")
            is AsyncState.Loaded -> {
                val currentId = state.value.current?.identifier
                val sortedOfferings = state.value.all.entries
                    .sortedWith(compareBy<Map.Entry<String, Offering>> { it.key != currentId }
                        .thenBy { it.key })

                sortedOfferings.forEachIndexed { index, (id, offering) ->
                    val isCurrent = id == currentId
                    OfferingRow(
                        offering = offering,
                        isCurrent = isCurrent,
                        onShowPaywallClick = { footer ->
                            if (footer) {
                                navigateTo(Screen.PaywallFooter(offering = offering, customVariables = customVariables))
                            } else {
                                navigateTo(Screen.Paywall(offering = offering, customVariables = customVariables))
                            }
                        },
                        onShowPaywallWithPurchaseLogicClick = {
                            navigateTo(Screen.PaywallWithPurchaseLogic(offering = offering, customVariables = customVariables))
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    if (index < sortedOfferings.lastIndex) {
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun OfferingRow(
    offering: Offering,
    isCurrent: Boolean,
    onShowPaywallClick: (footer: Boolean) -> Unit,
    onShowPaywallWithPurchaseLogicClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        collapsedContent = {
            Text(text = "${if (isCurrent) "\uD83D\uDC49 " else ""}${offering.identifier}")
        },
        expandedContent = {
            Column(
                modifier = Modifier
                    .padding(start = DefaultPaddingHorizontal)
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray.copy(alpha = 0.06f))
                        .padding(8.dp),
                ) {
                    PaywallsRow(
                        onShowFullscreenClick = { onShowPaywallClick(false) },
                        onShowFooterClick = { onShowPaywallClick(true) },
                        onShowFullscreenWithPurchaseLogicClick = onShowPaywallWithPurchaseLogicClick,
                    )
                }

                Text(text = "serverDescription: ${offering.serverDescription}")
                CollapsibleMapRow(map = offering.metadata, label = "metadata")

                offering.lifetime?.let {
                    PackageRow(pkg = it, label = "lifetime")
                } ?: Text(text = "lifetime: null")

                offering.annual?.let {
                    PackageRow(pkg = it, label = "annual")
                } ?: Text(text = "annual: null")

                offering.sixMonth?.let {
                    PackageRow(pkg = it, label = "sixMonth")
                } ?: Text(text = "sixMonth: null")

                offering.threeMonth?.let {
                    PackageRow(pkg = it, label = "threeMonth")
                } ?: Text(text = "threeMonth: null")

                offering.twoMonth?.let {
                    PackageRow(pkg = it, label = "twoMonth")
                } ?: Text(text = "twoMonth: null")

                offering.monthly?.let {
                    PackageRow(pkg = it, label = "monthly")
                } ?: Text(text = "monthly: null")

                offering.weekly?.let {
                    PackageRow(pkg = it, label = "weekly")
                } ?: Text(text = "weekly: null")

                PackagesRow(
                    pkgs = offering.availablePackages,
                    label = "availablePackages"
                )
            }
        },
        modifier = modifier
    )
}
