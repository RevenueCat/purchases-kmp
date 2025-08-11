package com.revenuecat.purchases.kmp.datetime

import com.revenuecat.purchases.kmp.models.Transaction
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * The purchase instant.
 */
@OptIn(ExperimentalTime::class)
public val Transaction.purchaseInstant: Instant
    get() = Instant.fromEpochMilliseconds(purchaseDateMillis)
