package com.revenuecat.purchases.kmp.models

/**
 * Type containing information of installment subscriptions. Currently only supported in Google Play.
 */
public data class InstallmentsInfo(
    /**
     * Number of payments the customer commits to in order to purchase the subscription.
     */
    public val commitmentPaymentsCount: Int,
    /**
     * After the commitment payments are complete, the number of payments the user commits to upon a renewal.
     */
    public val renewalCommitmentPaymentsCount: Int
)
