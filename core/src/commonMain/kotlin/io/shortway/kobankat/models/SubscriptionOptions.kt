package io.shortway.kobankat.models

/**
 * A collection of [SubscriptionOption]s.
 */
public expect class SubscriptionOptions  {

    /**
     * The base plan [SubscriptionOption].
     */
    public val basePlan: SubscriptionOption?


    /**
     * The first [SubscriptionOption] with a free trial [PricingPhase].
     */
    public val freeTrial: SubscriptionOption?


    /**
     * The first [SubscriptionOption] with an intro trial [PricingPhase].
     * There can be a free trial [PricingPhase] and intro trial [PricingPhase] in the same [SubscriptionOption].
     */
    public val introOffer: SubscriptionOption?


    /**
     * The default [SubscriptionOption]:
     *   - Filters out offers with "rc-ignore-offer" tag
     *   - Uses [SubscriptionOption] WITH longest free trial or cheapest first phase
     *   - Falls back to use base plan
     */
    public val defaultOffer: SubscriptionOption?


    /**
     * Finds all [SubscriptionOption]s with a specific tag.
     * Note: All offers inherit base plan tags.
     */
    public fun withTag(tag: String): List<SubscriptionOption>
}