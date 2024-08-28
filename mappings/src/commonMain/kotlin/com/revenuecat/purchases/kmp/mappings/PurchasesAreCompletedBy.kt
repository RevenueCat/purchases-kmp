package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.PurchasesAreCompletedBy

/**
 * Converts an instance of `PurchasesAreCompletedBy` to its corresponding string representation
 * suitable for usage with the PurchasesHybridCommon library.
 *
 * @return A `String` that represents the type of `PurchasesAreCompletedBy`:
 * - Returns `"REVENUECAT"` if the instance is of type `PurchasesAreCompletedBy.RevenueCat`.
 * - Returns `"MY_APP"` if the instance is of type `PurchasesAreCompletedBy.MyApp`.
 *
 */
public fun PurchasesAreCompletedBy.toHybridString(): String =
    when(this) {
        is PurchasesAreCompletedBy.RevenueCat -> "REVENUECAT"
        is PurchasesAreCompletedBy.MyApp -> "MY_APP"
    }
