package io.shortway.kobankat.apitester

import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.PresentedOfferingTargetingContext
import com.revenuecat.purchases.kmp.offeringIdentifier
import com.revenuecat.purchases.kmp.placementIdentifier
import com.revenuecat.purchases.kmp.revision
import com.revenuecat.purchases.kmp.ruleId
import com.revenuecat.purchases.kmp.targetingContext

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
        val revision: Int = targetingContext.revision
        val ruleID: String = targetingContext.ruleId
    }
}
