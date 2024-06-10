package com.revenuecat.purchases.kmp.models

/**
 * Representation of a transaction.
 */
public expect class Transaction

/**
 * Identifier of this transaction.
 */
public expect val Transaction.transactionIdentifier: String

/**
 * The identifier of the purchased product.
 */
public expect val Transaction.productIdentifier: String

/**
 * The purchase date in millis since the Unix epoch.
 */
public expect val Transaction.purchaseDateMillis: Long
