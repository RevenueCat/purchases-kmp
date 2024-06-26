package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.PresentedOfferingContext
import com.revenuecat.purchases.kmp.ProductType

/**
 * Represents an in-app product's or subscription's listing details.
 */
public expect class StoreProduct

/**
 * The product ID.
 * Google INAPP: "<productId>"
 * Google Sub: "<productId:basePlanID>"
 * Amazon INAPP: "<sku>"
 * Amazon Sub: "<termSku>"
 */
public expect val StoreProduct.id: String

/**
 * Type of product. One of [ProductType].
 */
public expect val StoreProduct.type: ProductType

/**
 * Price information for a non-subscription product.
 * Base plan price for a Google subscription.
 * Term price for an Amazon subscription.
 * For Google subscriptions, use SubscriptionOption's pricing phases for offer pricing.
 */
public expect val StoreProduct.price: Price

/**
 * Title of the product.
 *
 * If you are using Google subscriptions with multiple base plans, this title
 * will be the same for every subscription duration (monthly, yearly, etc) as
 * base plans don't have their own titles. Google suggests using the duration
 * as a way to title base plans.
 */
public expect val StoreProduct.title: String

/**
 * The description of the product.
 */
public expect val StoreProduct.localizedDescription: String?

/**
 * Subscription period.
 *
 * Note: Returned only for Google subscriptions. Null for Amazon or for INAPP products.
 */
public expect val StoreProduct.period: Period?

/**
 * Play Store only. Contains all [SubscriptionOption]s. Null for Amazon or for INAPP products.
 */
public expect val StoreProduct.subscriptionOptions: SubscriptionOptions?

/**
 * Play Store only. The default [SubscriptionOption] that will be used when purchasing and not
 * specifying a different option.
 * Null for INAPP products.
 */
public expect val StoreProduct.defaultOption: SubscriptionOption?

/**
 * App Store only. A list of subscription offers available for the auto-renewable subscription.
 */
public expect val StoreProduct.discounts: List<StoreProductDiscount>

/**
 * App Store only, null otherwise. The object containing introductory price information for the
 * product. If you’ve set up introductory prices in App Store Connect, the introductory price
 * property will be populated. This property is null if the product has no introductory price.
 */
public expect val StoreProduct.introductoryDiscount: StoreProductDiscount?

/**
 * Contains only data that is required to make the purchase.
 */
public expect val StoreProduct.purchasingData: PurchasingData

/**
 * The offering ID this `StoreProduct` was returned from.
 *
 * Null in the following cases:
 * - when running on iOS,
 * - if not using RevenueCat offerings system, or
 * - if fetched directly via `Purchases.getProducts`
 */
public expect val StoreProduct.presentedOfferingContext: PresentedOfferingContext?
