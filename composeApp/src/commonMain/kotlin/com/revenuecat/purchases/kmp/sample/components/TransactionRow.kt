package com.revenuecat.purchases.kmp.sample.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.kmp.datetime.purchaseInstant
import com.revenuecat.purchases.kmp.models.Transaction
import com.revenuecat.purchases.kmp.sample.DefaultPaddingHorizontal
import com.revenuecat.purchases.kmp.sample.DefaultSpacingVertical

@Composable
internal fun TransactionsRow(
    transactions: Collection<Transaction>,
    label: String,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        collapsedContent = { Text(text = "$label: ${transactions.size}") },
        expandedContent = {
            Column(
                modifier = Modifier.padding(start = DefaultPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
            ) {
                transactions.forEach { transaction -> TransactionRow(transaction) }
            }
        },
        modifier = modifier,
    )
}

@Composable
internal fun TransactionRow(
    transaction: Transaction,
    modifier: Modifier = Modifier,
) {
    CollapsibleRow(
        collapsedContent = { Text(text = transaction.transactionIdentifier) },
        expandedContent = {
            Column(
                modifier = Modifier.padding(start = DefaultPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(DefaultSpacingVertical),
            ) {
                Text(text = "productIdentifier: ${transaction.productIdentifier}")
                Text(text = "purchaseInstant: ${transaction.purchaseInstant}")
            }
        },
        modifier = modifier,
    )
}
