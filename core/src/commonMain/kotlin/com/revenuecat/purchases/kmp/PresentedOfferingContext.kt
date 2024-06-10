package com.revenuecat.purchases.kmp

/**
 * Contains data about the context in which an offering was presented.
 */
public expect class PresentedOfferingContext

/**
 * The targeting context used to obtain a [PresentedOfferingContext].
 */
public expect class PresentedOfferingTargetingContext

/**
 * The identifier of the offering used to obtain this object.
 */
public expect val PresentedOfferingContext.offeringIdentifier: String

/**
 * The identifier of the placement used to obtain this object.
 */
public expect val PresentedOfferingContext.placementIdentifier: String?

/**
 * The targeting context used to obtain this object.
 */
public expect val PresentedOfferingContext.targetingContext: PresentedOfferingTargetingContext?

/**
 * The revision of the targeting used to obtain this object.
 */
public expect val PresentedOfferingTargetingContext.revision: Int

/**
 * The rule id from the targeting used to obtain this object.
 */
public expect val PresentedOfferingTargetingContext.ruleId: String
