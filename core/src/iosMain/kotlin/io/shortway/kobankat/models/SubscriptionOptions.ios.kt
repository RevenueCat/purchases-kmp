package io.shortway.kobankat.models

public actual class SubscriptionOptions private constructor(
    public actual val basePlan: SubscriptionOption?,
    public actual val freeTrial: SubscriptionOption?,
    public actual val introOffer: SubscriptionOption?,
    public actual val defaultOffer: SubscriptionOption?,
) {
    public actual fun withTag(tag: String): List<SubscriptionOption> =
        error("SubscriptionOptions should not be instantiable on iOS.")
}