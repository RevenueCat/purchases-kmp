@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.PresentedOfferingContext as RcPresentedOfferingContext

public actual typealias PresentedOfferingContext = RcPresentedOfferingContext

public actual typealias PresentedOfferingTargetingContext = RcPresentedOfferingContext.TargetingContext

public actual val PresentedOfferingContext.offeringIdentifier: String
    get() = offeringIdentifier

public actual val PresentedOfferingContext.placementIdentifier: String?
    get() = placementIdentifier

public actual val PresentedOfferingContext.targetingContext: PresentedOfferingTargetingContext?
    get() = targetingContext

public actual val PresentedOfferingTargetingContext.revision: Int
    get() = revision

public actual val PresentedOfferingTargetingContext.ruleId: String
    get() = ruleId
