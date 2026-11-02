package com.revenuecat.purchases.kmp.models

/**
 * Enum indicating the billing plan type of an installment subscription. iOS only.
 */
public enum class BillingPlanType {
    /**
     * The customer pays in full when purchasing the product.
     */
    UP_FRONT,

    /**
     * The customer pays in monthly installments.
     */
    MONTHLY,

    /**
     * Unable to determine the billing plan type.
     */
    UNKNOWN,
}
