package io.shortway.kobankat.models

public expect class Transaction

public expect val Transaction.transactionIdentifier: String
public expect val Transaction.productIdentifier: String
public expect val Transaction.purchaseDateMillis: Long