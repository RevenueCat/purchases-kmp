package com.revenuecat.purchases.kmp.models

/**
 * An exception indicating an error occurred during a transaction. Extends [PurchasesException] by
 * adding a [userCancelled] property.
 */
public class PurchasesTransactionException(
    purchasesError: PurchasesError,
    /**
     * Whether the user cancelled the transaction.
     */
    public val userCancelled: Boolean,
) : PurchasesException(purchasesError)
