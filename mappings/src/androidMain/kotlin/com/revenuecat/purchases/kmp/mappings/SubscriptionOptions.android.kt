package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.models.SubscriptionOptions
import com.revenuecat.purchases.models.SubscriptionOptions as NativeAndroidSubscriptionOptions

internal fun NativeAndroidSubscriptionOptions.toSubscriptionOptions(): SubscriptionOptions =
    AndroidSubscriptionOptions(this)

private class AndroidSubscriptionOptions(val wrapped: NativeAndroidSubscriptionOptions): SubscriptionOptions {
    override val basePlan: SubscriptionOption? by lazy {
        wrapped.basePlan?.toSubscriptionOption()
    }
    override val freeTrial: SubscriptionOption? by lazy {
        wrapped.freeTrial?.toSubscriptionOption()
    }
    override val introOffer: SubscriptionOption? by lazy {
        wrapped.introOffer?.toSubscriptionOption()
    }
    override val defaultOffer: SubscriptionOption? by lazy {
        wrapped.defaultOffer?.toSubscriptionOption()
    }

    override fun withTag(tag: String): List<SubscriptionOption> {
        return wrapped.withTag(tag).map { it.toSubscriptionOption() }
    }

}
