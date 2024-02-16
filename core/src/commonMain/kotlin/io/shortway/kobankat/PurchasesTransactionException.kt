package io.shortway.kobankat

public class PurchasesTransactionException(
    purchasesError: PurchasesError,
    public val userCancelled: Boolean,
) : PurchasesException(purchasesError)