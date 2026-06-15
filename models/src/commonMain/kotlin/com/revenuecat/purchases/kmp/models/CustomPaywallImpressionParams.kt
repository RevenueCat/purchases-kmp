package com.revenuecat.purchases.kmp.models

/**
 * Parameters for tracking a custom paywall impression event.
 *
 * @param paywallId An optional identifier for the custom paywall being shown.
 * @param offeringId [Deprecated] Use [offering] instead. Optional identifier for the offering associated
 * with the custom paywall.
 * @property offering The [Offering] associated with the custom paywall, if provided.
 */
public class CustomPaywallImpressionParams private constructor(
    public val paywallId: String? = null,
    @Deprecated(
        message = "Pass an Offering object instead. Using an offering identifier string prevents " +
            "the SDK from deriving placement and targeting context automatically.",
    )
    public val offeringId: String? = null,
    public val offering: Offering?,
) {
    /**
     * Creates parameters with no offering information. No offering data is sent and the native SDK
     * chooses the current offering.
     */
    public constructor() : this(
        paywallId = null,
        offeringId = null,
        offering = null,
    )

    /**
     * Creates parameters with only a paywall identifier. No offering data is sent and the native SDK
     * chooses the current offering.
     *
     * @param paywallId An optional identifier for the custom paywall being shown.
     */
    public constructor(
        paywallId: String?,
    ) : this(
        paywallId = paywallId,
        offeringId = null,
        offering = null,
    )

    /**
     * Creates parameters with an offering identifier string.
     *
     * @param paywallId An optional identifier for the custom paywall being shown.
     * @param offeringId [Deprecated] Use [offering] instead. Optional identifier for the offering associated
     * with the custom paywall.
     * @deprecated Pass an [Offering] object instead. Using an offering identifier string prevents
     * the SDK from deriving placement and targeting context automatically.
     */
    @Deprecated(
        message = "Pass an Offering object instead. Using an offering identifier string prevents " +
            "the SDK from deriving placement and targeting context automatically.",
    )
    public constructor(
        paywallId: String? = null,
        offeringId: String?,
    ) : this(
        paywallId = paywallId,
        offeringId = offeringId,
        offering = null,
    )

    /**
     * Creates parameters from the offering the custom paywall was obtained from.
     *
     * Use this constructor when presenting a paywall for an offering that is not the current
     * offering, for example a placement-resolved offering. The SDK will derive both the offering
     * identifier and the presented offering context from the provided offering.
     *
     * @param paywallId An optional identifier for the custom paywall being shown.
     * @param offering The [Offering] associated with the custom paywall.
     */
    public constructor(
        offering: Offering,
        paywallId: String? = null,
    ) : this(paywallId, offering.identifier, offering)

    /**
     * Creates parameters from the offering the custom paywall was obtained from.
     *
     * When both [offering] and [offeringId] are provided, [offering] wins.
     *
     * @param paywallId An optional identifier for the custom paywall being shown.
     * @param offering The [Offering] associated with the custom paywall.
     * @param offeringId [Deprecated] Use [offering] instead. Ignored when [offering] is provided.
     */
    @Deprecated(
        message = "Pass only an Offering object instead. The offeringId is ignored when Offering is provided.",
        replaceWith = ReplaceWith("CustomPaywallImpressionParams(offering, paywallId)"),
    )
    public constructor(
        paywallId: String? = null,
        offering: Offering,
        @Suppress("UNUSED_PARAMETER")
        offeringId: String?,
    ) : this(paywallId, offering.identifier, offering)
}
