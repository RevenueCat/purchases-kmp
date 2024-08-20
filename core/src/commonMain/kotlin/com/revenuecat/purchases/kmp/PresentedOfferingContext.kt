package com.revenuecat.purchases.kmp

/**
 * Contains data about the context in which an offering was presented.
 */
public expect class PresentedOfferingContext {

    /**
     * The identifier of the offering used to obtain this object.
     */
    public val offeringIdentifier: String

    /**
     * The identifier of the placement used to obtain this object.
     */
    public val placementIdentifier: String?

    /**
     * The targeting context used to obtain this object.
     */
    public val targetingContext: PresentedOfferingTargetingContext?
}

/**
 * The targeting context used to obtain a [PresentedOfferingContext].
 */
public expect class PresentedOfferingTargetingContext {

    /**
     * The revision of the targeting used to obtain this object.
     */
    public val revision: Int

    /**
     * The rule id from the targeting used to obtain this object.
     */
    public val ruleId: String
}
