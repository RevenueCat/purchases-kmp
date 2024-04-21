package io.shortway.kobankat.apitester

import io.shortway.kobankat.PresentedOfferingContext
import io.shortway.kobankat.PresentedOfferingTargetingContext
import io.shortway.kobankat.offeringIdentifier
import io.shortway.kobankat.placementIdentifier
import io.shortway.kobankat.revision
import io.shortway.kobankat.ruleId
import io.shortway.kobankat.targetingContext

@Suppress("unused", "UNUSED_VARIABLE")
private class PresentedOfferingContextAPI {
    fun check(presentedOfferingContext: PresentedOfferingContext) {
        val offeringIdentifier: String = presentedOfferingContext.offeringIdentifier
        val placementIdentifier: String? = presentedOfferingContext.placementIdentifier
        val targetingContext: PresentedOfferingTargetingContext? =
            presentedOfferingContext.targetingContext
    }

}

@Suppress("unused", "UNUSED_VARIABLE")
private class TargetingContextAPI {
    fun check(targetingContext: PresentedOfferingTargetingContext) {
        val revision: Long = targetingContext.revision
        val ruleID: String = targetingContext.ruleId
    }
}
