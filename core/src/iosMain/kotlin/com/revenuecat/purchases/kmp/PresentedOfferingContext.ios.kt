package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCPresentedOfferingContext
import cocoapods.PurchasesHybridCommon.RCTargetingContext

public actual typealias PresentedOfferingContext = RCPresentedOfferingContext

public actual typealias PresentedOfferingTargetingContext = RCTargetingContext


public actual val PresentedOfferingContext.offeringIdentifier: String
    get() = offeringIdentifier()

public actual val PresentedOfferingContext.placementIdentifier: String?
    get() = placementIdentifier()

public actual val PresentedOfferingContext.targetingContext: PresentedOfferingTargetingContext?
    get() = targetingContext()

public actual val PresentedOfferingTargetingContext.revision: Int
    get() = revision().toInt()

public actual val PresentedOfferingTargetingContext.ruleId: String
    get() = ruleId()
