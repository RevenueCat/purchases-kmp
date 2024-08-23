package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.ProductType

/**
 * Represents an in-app product's or subscription's listing details.
 */
public expect class StoreProduct {

    /**
     * The product ID.
     * Google INAPP: "<productId>"
     * Google Sub: "<productId:basePlanID>"
     * Amazon INAPP: "<sku>"
     * Amazon Sub: "<termSku>"
     */
    public val id: String

    /**
     * Type of product. One of [ProductType].
     */
    public val type: ProductType

    /**
     * Category of product. One of [ProductCategory]. Is never null on iOS, but will be null on Android
     * if the [type] is [ProductType.UNKNOWN].
     */
    public val category: ProductCategory?

    /**
     * Price information for a non-subscription product.
     * Base plan price for a Google subscription.
     * Term price for an Amazon subscription.
     * For Google subscriptions, use SubscriptionOption's pricing phases for offer pricing.
     */
    public val price: Price

    /**
     * Title of the product.
     * If you are using Google subscriptions with multiple base plans, this title
     * will be the same for every subscription duration (monthly, yearly, etc) as
     * base plans don't have their own titles. Google suggests using the duration
     * as a way to title base plans.
     */
    public val title: String

    /**
     * The description of the product.
     */
    public val localizedDescription: String?

    /**
     * Subscription period.
     * Note: Returned only for Google subscriptions. Null for Amazon or for INAPP products.
     */
    public val period: Period?

    /**
     * Play Store only. Contains all [SubscriptionOption]s. Null for Amazon or for INAPP products.
     */
    public val subscriptionOptions: SubscriptionOptions?

    /**
     * Play Store only. The default [SubscriptionOption] that will be used when purchasing and not
     * specifying a different option.
     * Null for INAPP products.
     */
    public val defaultOption: SubscriptionOption?

    /**
     * App Store only. A list of subscription offers available for the auto-renewable subscription.
     */
    public val discounts: List<StoreProductDiscount>

    /**
     * App Store only, null otherwise. The object containing introductory price information for the
     * product. If you’ve set up introductory prices in App Store Connect, the introductory price
     * property will be populated. This property is null if the product has no introductory price.
     */
    public val introductoryDiscount: StoreProductDiscount?

    /**
     * Contains only data that is required to make the purchase.
     */
    public val purchasingData: PurchasingData

    /**
     * The offering ID this `StoreProduct` was returned from.
     *
     * Null in the following cases:
     * - when running on iOS,
     * - if not using RevenueCat offerings system, or
     * - if fetched directly via `Purchases.getProducts`
     */
    public val presentedOfferingContext: PresentedOfferingContext?
}
