package com.revenuecat.purchases.kmp.models

/**
 * A collection of [SubscriptionOption]s.
 */
public class SubscriptionOptions internal constructor(
    // These are lambdas because the underlying Android properties are computed and use firstOrNull
    // Which can potentially be expensive
    private val getBasePlan: () -> SubscriptionOption?,
    private val getFreeTrial: () -> SubscriptionOption?,
    private val getIntroOffer: () -> SubscriptionOption?,
    private val getDefaultOffer: () -> SubscriptionOption?,
    private val withTag: (tag: String) -> List<SubscriptionOption>,
) {

    /**
     * The base plan [SubscriptionOption].
     */
    public val basePlan: SubscriptionOption?
        get() = getBasePlan()

    /**
     * The first [SubscriptionOption] with a free trial [PricingPhase].
     */
    public val freeTrial: SubscriptionOption?
        get() = getFreeTrial()

    /**
     * The first [SubscriptionOption] with an intro trial [PricingPhase].
     * There can be a free trial [PricingPhase] and intro trial [PricingPhase] in the same [SubscriptionOption].
     */
    public val introOffer: SubscriptionOption?
        get() = getIntroOffer()

    /**
     * The default [SubscriptionOption]:
     *   - Filters out offers with "rc-ignore-offer" tag
     *   - Uses [SubscriptionOption] WITH longest free trial or cheapest first phase
     *   - Falls back to use base plan
     */
    public val defaultOffer: SubscriptionOption?
        get() = getDefaultOffer()

    /**
     * Finds all [SubscriptionOption]s with a specific tag.
     * Note: All offers inherit base plan tags.
     */
    public fun withTag(tag: String): List<SubscriptionOption> =
        withTag.invoke(tag)

}
