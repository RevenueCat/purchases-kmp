package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.models.InstallmentsInfo
import com.revenuecat.purchases.kmp.models.PricingPhase
import com.revenuecat.purchases.kmp.models.PurchasingData
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.models.SubscriptionOption as NativeAndroidSubscriptionOption

public fun NativeAndroidSubscriptionOption.toSubscriptionOption(): SubscriptionOption =
    AndroidSubscriptionOption(this)

public fun SubscriptionOption.toNativeAndroidSubscriptionOption(): NativeAndroidSubscriptionOption =
    (this as AndroidSubscriptionOption).wrapped

internal class AndroidSubscriptionOption(
    val wrapped: NativeAndroidSubscriptionOption
): SubscriptionOption {
    override val id: String = wrapped.id
    override val pricingPhases: List<PricingPhase> =
        wrapped.pricingPhases.map { it.toPricingPhase() }
    override val tags: List<String> = wrapped.tags
    @Deprecated(
        "Use presentedOfferingContext instead",
        replaceWith = ReplaceWith("presentedOfferingContext.offeringIdentifier")
    )
    @Suppress("DEPRECATION")
    override val presentedOfferingIdentifier: String? = wrapped.presentedOfferingIdentifier
    override val presentedOfferingContext: PresentedOfferingContext? =
        wrapped.presentedOfferingContext?.toPresentedOfferingContext()
    override val purchasingData: PurchasingData =
        AndroidPurchasingData(wrapped.purchasingData)
    override val installmentsInfo: InstallmentsInfo? = wrapped.installmentsInfo?.toInstallmentsInfo()
}
