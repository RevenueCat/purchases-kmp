package com.revenuecat.purchases.kmp.models

/**
 * Type containing information of installment subscriptions. Currently only supported in Google Play.
 */
public actual interface InstallmentsInfo {
    /**
     * Number of payments the customer commits to in order to purchase the subscription.
     */
    public actual val commitmentPaymentsCount: Int

    /**
     * After the commitment payments are complete, the number of payments the user commits to upon a renewal.
     */
    public actual val renewalCommitmentPaymentsCount: Int

}

private data class IosInstallmentsInfo(
    override val commitmentPaymentsCount: Int,
    override val renewalCommitmentPaymentsCount: Int
): InstallmentsInfo
