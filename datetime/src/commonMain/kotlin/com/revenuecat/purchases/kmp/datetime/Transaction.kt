package com.revenuecat.purchases.kmp.datetime

import com.revenuecat.purchases.kmp.models.Transaction
import kotlinx.datetime.Instant

/**
 * The purchase instant.
 */
public val Transaction.purchaseInstant: Instant
    get() = Instant.fromEpochMilliseconds(purchaseDateMillis)
