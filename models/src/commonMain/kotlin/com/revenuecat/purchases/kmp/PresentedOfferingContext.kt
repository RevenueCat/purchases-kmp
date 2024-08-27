package com.revenuecat.purchases.kmp

/**
 * Contains data about the context in which an offering was presented.
 * @property offeringIdentifier The identifier of the offering used to obtain this object.
 * @property placementIdentifier The identifier of the placement used to obtain this object.
 * @property targetingContext The targeting context used to obtain this object.
 */
public class PresentedOfferingContext(
    public val offeringIdentifier: String,
    public val placementIdentifier: String?,
    public val targetingContext: PresentedOfferingTargetingContext?
)

/**
 * The targeting context used to obtain a [PresentedOfferingContext].
 * @property revision The revision of the targeting used to obtain this object.
 * @property ruleId The rule id from the targeting used to obtain this object.
 */
public class PresentedOfferingTargetingContext(
    public val revision: Int,
    public val ruleId: String
)
