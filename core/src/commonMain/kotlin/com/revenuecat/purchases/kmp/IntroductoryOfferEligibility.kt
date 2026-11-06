package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.PurchasesErrorCode

internal fun introductoryOfferEligibilityError(token: String, supported: Boolean): PurchasesError? = when {
    token.isEmpty() -> PurchasesError(
        PurchasesErrorCode.PurchaseInvalidError,
        "introductoryOfferEligibilityJWS must be a non-empty string."
    )
    !supported -> PurchasesError(
        PurchasesErrorCode.UnsupportedError,
        "Introductory offer eligibility JWS requires a supported Apple StoreKit 2 product and OS."
    )
    else -> null
}
