package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.models.PurchasesErrorCode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class IntroductoryOfferEligibilityTest {
    @Test fun emptyTokensAreInvalidEvenOnUnsupportedPlatforms() {
        for (supported in listOf(false, true)) {
            assertEquals(PurchasesErrorCode.PurchaseInvalidError, introductoryOfferEligibilityError("", supported)?.code)
        }
    }
    @Test fun denialTokensAreRejectedOnUnsupportedPaths() {
        assertEquals(PurchasesErrorCode.UnsupportedError,
            introductoryOfferEligibilityError("header.deny.signature", supported = false)?.code)
    }
    @Test fun supportedPathsPreserveOpaqueTokensForNativeValidation() {
        assertNull(introductoryOfferEligibilityError("header.deny.signature", supported = true))
        assertNull(introductoryOfferEligibilityError("malformed", supported = true))
    }
}
