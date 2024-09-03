package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.Package
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal

@Composable
internal fun PackagesRow(
    pkgs: List<Package>,
    label: String,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        modifier = modifier,
        collapsedContent = { Text(text = "$label: ${pkgs.size}") },
        expandedContent = {
            Column(modifier = Modifier.padding(start = DefaultPaddingHorizontal)) {
                pkgs.forEach { pkg -> PackageRow(pkg = pkg) }
            }
        },
    )
}

@Composable
internal fun PackageRow(
    pkg: Package,
    modifier: Modifier = Modifier,
    label: String = "package"
) {
    CollapsibleRow(
        collapsedContent = { Text(text = "$label: ${pkg.identifier}") },
        expandedContent = {
            Column(modifier = Modifier.padding(start = DefaultPaddingHorizontal)) {
                Text(text = "type: ${pkg.packageType}")
                PresentedOfferingContextRow(context = pkg.presentedOfferingContext)
                StoreProductRow(product = pkg.storeProduct)
            }
        },
        modifier = modifier,
    )
}
