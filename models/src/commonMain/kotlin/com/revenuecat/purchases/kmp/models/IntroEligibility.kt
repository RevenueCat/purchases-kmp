package com.revenuecat.purchases.kmp.models

public class IntroEligibility(
    public val status: Status,
) {
    public enum class Status {
        UNKNOWN,
        INELIGIBLE,
        ELIGIBLE,
        NO_INTRO_OFFER_EXISTS,
    }

    public val description: String = when (status) {
        Status.UNKNOWN -> "Unknown status"
        Status.INELIGIBLE -> "Not eligible for trial or introductory price."
        Status.ELIGIBLE -> "Eligible for trial or introductory price."
        Status.NO_INTRO_OFFER_EXISTS -> "Product does not have trial or introductory price."
    }
}
