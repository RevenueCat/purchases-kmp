package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.Offering
import com.revenuecat.purchases.kmp.Offerings
import com.revenuecat.purchases.kmp.sample.AsyncState
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal

@Composable
internal fun OfferingsSection(
    state: AsyncState<Offerings>,
    onShowPaywallClick: (offering: Offering?, footer: Boolean) -> Unit,
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

                state.value.all.forEach { (id, offering) ->
                    val isCurrent = id == state.value.current?.identifier
                    OfferingRow(
                        offering = offering,
                        isCurrent = isCurrent,
                        onShowPaywallClick = { footer -> onShowPaywallClick(offering, footer) },
                        modifier = Modifier.fillMaxWidth(),
                    )
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
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        collapsedContent = {
            Text(text = "${if (isCurrent) "\uD83D\uDC49 " else ""}${offering.identifier}")
        },
        expandedContent = {
            Column(modifier = Modifier.padding(start = DefaultPaddingHorizontal)) {
                PaywallsRow(
                    onShowFullscreenClick = { onShowPaywallClick(false) },
                    onShowFooterClick = { onShowPaywallClick(true) }
                )

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
