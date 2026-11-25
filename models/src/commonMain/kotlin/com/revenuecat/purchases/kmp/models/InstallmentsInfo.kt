package com.revenuecat.purchases.kmp.models

/**
 * Type containing information of installment subscriptions.
 * Supported in Google Play (via [SubscriptionOption]) and iOS (via [StoreProduct]).
 */
public class InstallmentsInfo(
    /**
     * Number of payments the customer commits to in order to purchase the subscription.
     */
    public val commitmentPaymentsCount: Int,
    /**
     * After the commitment payments are complete, the number of payments the user commits to upon a renewal.
     */
    public val renewalCommitmentPaymentsCount: Int,
    /**
     * Duration of each installment.
     * iOS only. Null on Android.
     */
    public val commitmentInstallmentPeriod: Period?,
    /**
     * Price charged for each installment billing period.
     * iOS only. Null on Android.
     */
    public val installmentBillingPrice: Price?,
    /**
     * Total duration of the customer's installment commitment.
     * iOS only. Null on Android.
     */
    public val commitmentTotalPeriod: Period?,
    /**
     * Total price the customer commits to paying across all installments.
     * iOS only. Null on Android.
     */
    public val commitmentTotalPrice: Price?,
    /**
     * The billing plan used for the installments.
     * iOS only. Null on Android.
     */
    public val billingPlanType: BillingPlanType?,
) {

    // @JvmOverloads doesn't work for ObjC.
    public constructor(
        /**
         * Number of payments the customer commits to in order to purchase the subscription.
         */
        commitmentPaymentsCount: Int,
        /**
         * After the commitment payments are complete, the number of payments the user commits to upon a renewal.
         */
        renewalCommitmentPaymentsCount: Int,
    ) : this(
        commitmentPaymentsCount = commitmentPaymentsCount,
        renewalCommitmentPaymentsCount = renewalCommitmentPaymentsCount,
        commitmentInstallmentPeriod = null,
        installmentBillingPrice = null,
        commitmentTotalPeriod = null,
        commitmentTotalPrice = null,
        billingPlanType = null,
    )
}
