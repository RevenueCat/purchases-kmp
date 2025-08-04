package com.revenuecat.purchases.kmp.ktx

import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.CacheFetchPolicy
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.GoogleReplacementMode
import com.revenuecat.purchases.kmp.models.IntroEligibilityStatus
import com.revenuecat.purchases.kmp.models.Offerings
import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.models.PromotionalOffer
import com.revenuecat.purchases.kmp.models.PurchasesException
import com.revenuecat.purchases.kmp.models.PurchasesTransactionException
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.models.Storefront
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.models.VirtualCurrencies
import com.revenuecat.purchases.kmp.models.WinBackOffer
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * The result of a successful purchase operation. Used in coroutines.
 */
public class SuccessfulPurchase(
    /**
     * The [StoreTransaction] for this purchase.
     */
    public val storeTransaction: StoreTransaction,

    /**
     * The updated [CustomerInfo] for this user after the purchase has been synced with
     * RevenueCat's servers.
     */
    public val customerInfo: CustomerInfo,
)

/**
 * The result of a successful login operation. Used in coroutines.
 */
public class SuccessfulLogin(
    /**
     * The [CustomerInfo] associated with the logged in user.
     */
    public val customerInfo: CustomerInfo,

    /**
     * true if a new user has been registered in the backend,
     * false if the user had already been registered.
     */
    public val created: Boolean,
)

/**
 * This method will send all the purchases to the RevenueCat backend. Call this when using your own
 * implementation for subscriptions anytime a sync is needed, such as when migrating existing users
 * to RevenueCat.
 *
 * **Warning:** This function should only be called if you're migrating to RevenueCat or in observer
 * mode.
 *
 * **Warning:** This function could take a relatively long time to execute, depending on the amount
 * of purchases the user has. Consider that when waiting for this operation to complete.
 *
 * @throws PurchasesException in case of an error.
 */
@Throws(PurchasesException::class, CancellationException::class)
public suspend fun Purchases.awaitSyncPurchases(): CustomerInfo = suspendCoroutine { continuation ->
    syncPurchases(
        onError = { continuation.resumeWithException(PurchasesException(it)) },
        onSuccess = { continuation.resume(it) }
    )
}


/**
 * Syncs subscriber attributes and then fetches the configured offerings for this user. This method
 * is intended to be called when using Targeting Rules with Custom Attributes. Any subscriber
 * attributes should be set before calling this method to ensure the returned offerings are applied
 * with the latest subscriber attributes.
 *
 * This method is rate limited to 5 calls per minute. It will log a warning and return offerings
 * cache when reached.
 *
 * Refer to [the guide](https://www.revenuecat.com/docs/tools/targeting) for more targeting
 * information.
 * For more offerings information, see [Purchases.getOfferings].
 *
 * Coroutine friendly version of [Purchases.syncAttributesAndOfferingsIfNeeded].
 *
 * @return The [Offerings] fetched after syncing attributes.
 *
 * @throws [PurchasesException] with the first error if there's an error syncing attributes or
 * fetching offerings.
 */
@Throws(PurchasesException::class, CancellationException::class)
public suspend fun Purchases.awaitSyncAttributesAndOfferingsIfNeeded(): Offerings =
    suspendCoroutine { continuation ->
        syncAttributesAndOfferingsIfNeeded(
            onError = { continuation.resumeWithException(PurchasesException(it)) },
            onSuccess = { continuation.resume(it) }
        )
    }

/**
 * Fetch the configured offerings for this users. Offerings allows you to configure your in-app
 * products vis RevenueCat and greatly simplifies management. See
 * [the guide](https://docs.revenuecat.com/offerings) for more info.
 *
 * Offerings will be fetched and cached on instantiation so that, by the time they are needed,
 * your prices are loaded for your purchase flow. Time is money.
 *
 * @throws PurchasesException in case of an error.
 */
@Throws(PurchasesException::class, CancellationException::class)
public suspend fun Purchases.awaitOfferings(): Offerings = suspendCoroutine { continuation ->
    getOfferings(
        onError = { continuation.resumeWithException(PurchasesException(it)) },
        onSuccess = { continuation.resume(it) }
    )
}

/**
 * Gets the [StoreProduct]s for the given list of product ids for all product types.
 *
 * @throws PurchasesException in case of an error.
 */
@Throws(PurchasesException::class, CancellationException::class)
public suspend fun Purchases.awaitGetProducts(
    productIds: List<String>
): List<StoreProduct> = suspendCoroutine { continuation ->
    getProducts(
        productIds = productIds,
        onError = { continuation.resumeWithException(PurchasesException(it)) },
        onSuccess = { continuation.resume(it) }
    )
}

/**
 * App Store only. Use this method to fetch a [PromotionalOffer] to use with [awaitPurchase].
 *
 * @throws PurchasesException in case of an error.
 */
@Throws(PurchasesException::class, CancellationException::class)
public suspend fun Purchases.awaitPromotionalOffer(
    discount: StoreProductDiscount,
    storeProduct: StoreProduct,
): PromotionalOffer = suspendCoroutine { continuation ->
    getPromotionalOffer(
        discount = discount,
        storeProduct = storeProduct,
        onError = { continuation.resumeWithException(PurchasesException(it)) },
        onSuccess = { continuation.resume(it) }
    )
}

/**
 * Purchases [storeProduct].
 * On the Play Store, if [storeProduct] represents a subscription, upgrades from the subscription
 * specified by [oldProductId] and chooses [storeProduct]'s default [SubscriptionOption].
 *
 * The default [SubscriptionOption] logic:
 *   - Filters out offers with `"rc-ignore-offer"` tag
 *   - Uses [SubscriptionOption] WITH longest free trial or cheapest first phase
 *   - Falls back to use base plan
 *
 * If [storeProduct] represents a non-subscription, [oldProductId] and [replacementMode] will be
 * ignored.
 *
 * @param storeProduct The [StoreProduct] you wish to purchase.
 * @param isPersonalizedPrice Play Store only, ignored otherwise. Optional boolean indicates
 * personalized pricing on products available for purchase in the EU. For compliance with EU
 * regulations. User will see "This price has been customize for you" in the purchase dialog when
 * true. See
 * [developer.android.com](https://developer.android.com/google/play/billing/integrate#personalized-price)
 * for more info.
 * @param oldProductId Play Store only, ignored otherwise. If this purchase is an upgrade from
 * another product, provide the previous product ID here.
 * @param replacementMode Play Store only, ignored otherwise. The replacement mode to use when
 * upgrading from another product. This field is ignored, unless [oldProductId] is non-null.
 *
 * @throws PurchasesTransactionException in case of an error.
 */
@Throws(PurchasesTransactionException::class, CancellationException::class)
public suspend fun Purchases.awaitPurchase(
    storeProduct: StoreProduct,
    isPersonalizedPrice: Boolean? = null,
    oldProductId: String? = null,
    replacementMode: GoogleReplacementMode = GoogleReplacementMode.WITHOUT_PRORATION,
): SuccessfulPurchase = suspendCoroutine { continuation ->
    purchase(
        storeProduct = storeProduct,
        onError = { error, userCancelled ->
            continuation.resumeWithException(
                PurchasesTransactionException(error, userCancelled)
            )
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo))
        },
        isPersonalizedPrice = isPersonalizedPrice,
        oldProductId = oldProductId,
        replacementMode = replacementMode,
    )
}

/**
 * Purchases [packageToPurchase].
 * On the Play Store, if [packageToPurchase] represents a subscription, upgrades from the
 * subscription specified by [oldProductId] and chooses the default [SubscriptionOption] from
 * [packageToPurchase].
 *
 * The default [SubscriptionOption] logic:
 *   - Filters out offers with `"rc-ignore-offer"` tag
 *   - Uses [SubscriptionOption] WITH longest free trial or cheapest first phase
 *   - Falls back to use base plan
 *
 * If [packageToPurchase] represents a non-subscription, [oldProductId] and [replacementMode] will
 * be ignored.
 *
 * @param packageToPurchase The [Package] you wish to purchase.
 * @param isPersonalizedPrice Play Store only, ignored otherwise. Optional boolean indicates
 * personalized pricing on products available for purchase in the EU. For compliance with EU
 * regulations. User will see "This price has been customize for you" in the purchase dialog when
 * true. See
 * [developer.android.com](https://developer.android.com/google/play/billing/integrate#personalized-price)
 * for more info.
 * @param oldProductId Play Store only, ignored otherwise. If this purchase is an upgrade from
 * another product, provide the previous product ID here.
 * @param replacementMode Play Store only, ignored otherwise. The replacement mode to use when
 * upgrading from another product. This field is ignored, unless [oldProductId] is non-null.
 *
 * @throws PurchasesTransactionException in case of an error.
 */
@Throws(PurchasesTransactionException::class, CancellationException::class)
public suspend fun Purchases.awaitPurchase(
    packageToPurchase: Package,
    isPersonalizedPrice: Boolean? = null,
    oldProductId: String? = null,
    replacementMode: GoogleReplacementMode = GoogleReplacementMode.WITHOUT_PRORATION,
): SuccessfulPurchase = suspendCoroutine { continuation ->
    purchase(
        packageToPurchase = packageToPurchase,
        onError = { error, userCancelled ->
            continuation.resumeWithException(
                PurchasesTransactionException(error, userCancelled)
            )
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo))
        },
        isPersonalizedPrice = isPersonalizedPrice,
        oldProductId = oldProductId,
        replacementMode = replacementMode,
    )
}

/**
 * Play Store only. Purchases [subscriptionOption].
 *
 * @param subscriptionOption The [SubscriptionOption] you wish to purchase.
 * @param isPersonalizedPrice Play Store only, ignored otherwise. Optional boolean indicates
 * personalized pricing on products available for purchase in the EU. For compliance with EU
 * regulations. User will see "This price has been customize for you" in the purchase dialog when
 * true. See
 * [developer.android.com](https://developer.android.com/google/play/billing/integrate#personalized-price)
 * for more info.
 * @param oldProductId Play Store only, ignored otherwise. If this purchase is an upgrade from
 * another product, provide the previous product ID here.
 * @param replacementMode Play Store only, ignored otherwise. The replacement mode to use when
 * upgrading from another product. This field is ignored, unless [oldProductId] is non-null.
 *
 * @throws PurchasesTransactionException in case of an error.
 */
@Throws(PurchasesTransactionException::class, CancellationException::class)
public suspend fun Purchases.awaitPurchase(
    subscriptionOption: SubscriptionOption,
    isPersonalizedPrice: Boolean? = null,
    oldProductId: String? = null,
    replacementMode: GoogleReplacementMode = GoogleReplacementMode.WITHOUT_PRORATION,
): SuccessfulPurchase = suspendCoroutine { continuation ->
    purchase(
        subscriptionOption = subscriptionOption,
        onError = { error, userCancelled ->
            continuation.resumeWithException(
                PurchasesTransactionException(error, userCancelled)
            )
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo))
        },
        isPersonalizedPrice = isPersonalizedPrice,
        oldProductId = oldProductId,
        replacementMode = replacementMode,
    )
}

/**
 * App Store only. Use this function if you are not using the Offerings system to purchase a
 * [StoreProduct] with an applied PromotionalOffer. If you are using the Offerings system, use the
 * overload with a [Package] parameter instead.
 *
 * @param storeProduct The [StoreProduct] you wish to purchase.
 * @param promotionalOffer The [PromotionalOffer] to apply to this purchase.
 * [StoreTransaction] and updated [CustomerInfo].
 *
 * @throws PurchasesTransactionException in case of an error.
 *
 * @see [awaitPromotionalOffer]
 */
@Throws(PurchasesTransactionException::class, CancellationException::class)
public suspend fun Purchases.awaitPurchase(
    storeProduct: StoreProduct,
    promotionalOffer: PromotionalOffer,
): SuccessfulPurchase = suspendCoroutine { continuation ->
    purchase(
        storeProduct = storeProduct,
        promotionalOffer = promotionalOffer,
        onError = { error, userCancelled ->
            continuation.resumeWithException(
                PurchasesTransactionException(error, userCancelled)
            )
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo))
        },
    )
}

/**
 * App Store only. Purchases [packageToPurchase]. Call this method when a user has decided to
 * purchase a product with an applied discount. Only call this in direct response to user input.
 * From here [Purchases] will handle the purchase with StoreKit.
 *
 * @param packageToPurchase The [Package] you wish to purchase.
 * @param promotionalOffer The [PromotionalOffer] to apply to this purchase.
 * [StoreTransaction] and updated [CustomerInfo].
 *
 * @throws PurchasesTransactionException in case of an error.
 *
 * @see [awaitPromotionalOffer]
 */
@Throws(PurchasesTransactionException::class, CancellationException::class)
public suspend fun Purchases.awaitPurchase(
    packageToPurchase: Package,
    promotionalOffer: PromotionalOffer,
): SuccessfulPurchase = suspendCoroutine { continuation ->
    purchase(
        packageToPurchase = packageToPurchase,
        promotionalOffer = promotionalOffer,
        onError = { error, userCancelled ->
            continuation.resumeWithException(
                PurchasesTransactionException(error, userCancelled)
            )
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo))
        },
    )
}

/**
 * iOS only. Android always returns [IntroEligibilityStatus.UNKNOWN].
 *
 * Computes whether or not a user is eligible for the introductory pricing period of a given
 * [products]. You should use this method to determine whether or not you show the user the
 * normal product price or the introductory price. This also applies to trials. (Trials are
 * considered a type of introductory pricing.)
 *
 * **Note**
 *
 * Subscription groups are automatically collected for determining eligibility. If RevenueCat
 * can't definitively compute the eligibility, most likely because of missing group information,
 * it will return [IntroEligibilityStatus.UNKNOWN]. The best course of action on unknown status
 * is to display the non-intro pricing, to not create a misleading situation. To avoid this,
 * make sure you are testing with the latest version of iOS so that the subscription group can
 * be collected by the SDK.
 *
 * For more information, see
 * [our documentation](https://www.revenuecat.com/docs/subscription-guidance/subscription-offers/ios-subscription-offers).
 */
@Throws(CancellationException::class)
public suspend fun Purchases.awaitTrialOrIntroPriceEligibility(
    products: List<StoreProduct>
): Map<StoreProduct, IntroEligibilityStatus> = suspendCoroutine { continuation ->
    checkTrialOrIntroPriceEligibility(products) { result ->
        continuation.resume(result)
    }
}

/**
 * Fetches the win-back offers that a subscriber is eligible for on a given product.
 *
 * iOS only. Requires iOS 18.0+ and StoreKit 2 and throws an error if
 * these requirements aren't met.
 *
 * @param storeProduct: The product to check for eligible win-back offers on.
 *
 * @throws PurchasesException in case of an error.
 *
 * @return A [List] of eligible [WinBackOffer]s.
 */
@Throws(PurchasesException::class, CancellationException::class)
public suspend fun Purchases.awaitEligibleWinBackOffersForProduct(
    storeProduct: StoreProduct
): List<WinBackOffer> = suspendCoroutine { continuation ->
    getEligibleWinBackOffersForProduct(
        storeProduct = storeProduct,
        onError = { error ->
            continuation.resumeWithException(
                PurchasesException(error = error)
            )
        },
        onSuccess = { eligibleWinBackOffers ->
            continuation.resume(eligibleWinBackOffers)
        }
    )
}

/**
 * Fetches the win-back offers that a subscriber is eligible for on a given package.
 *
 * iOS only. Requires iOS 18.0+ and StoreKit 2 and throws an error if
 * these requirements aren't met.
 *
 * @param packageToCheck: The package to check for eligible win-back offers on.
 *
 * @throws PurchasesException in case of an error.
 *
 * @return A [List] of eligible [WinBackOffer]s.
 */
@Throws(PurchasesException::class, CancellationException::class)
public suspend fun Purchases.awaitEligibleWinBackOffersForPackage(
    packageToCheck: Package
): List<WinBackOffer> = suspendCoroutine { continuation ->
    getEligibleWinBackOffersForPackage(
        packageToCheck = packageToCheck,
        onError = { error ->
            continuation.resumeWithException(
                PurchasesException(error = error)
            )
        },
        onSuccess = { eligibleWinBackOffers ->
            continuation.resume(eligibleWinBackOffers)
        }
    )
}

/**
 * Purchases a product with a given win-back offer. If you are using the Offerings system, use the
 * overload with a [Package] parameter instead.
 *
 * iOS only. Requires iOS 18.0+ and StoreKit 2 and returns a result with an
 * error if these requirements aren't met.
 *
 * @param storeProduct: The product to purchase
 * @param winBackOffer: The win-back offer to apply to the purchase
 *
 * @return A [SuccessfulPurchase] if the purchase is successful
 *
 * @see [awaitEligibleWinBackOffersForProduct]
 */
@Throws(PurchasesTransactionException::class, CancellationException::class)
public suspend fun Purchases.awaitPurchase(
    storeProduct: StoreProduct,
    winBackOffer: WinBackOffer
): SuccessfulPurchase = suspendCoroutine { continuation ->
    purchase(
        storeProduct = storeProduct,
        winBackOffer = winBackOffer,
        onError = { error, userCancelled ->
            continuation.resumeWithException(
                PurchasesTransactionException(error, userCancelled)
            )
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo))
        }
    )
}

/**
 * Purchases a package with a given win-back offer.
 *
 * iOS only. Requires iOS 18.0+ and StoreKit 2 and returns a result with an
 * error if these requirements aren't met.
 *
 * @param packageToPurchase: The package to purchase
 * @param winBackOffer: The win-back offer to apply to the purchase
 *
 * @return A [SuccessfulPurchase] if the purchase is successful
 *
 * @see [awaitEligibleWinBackOffersForPackage]
 */
@Throws(PurchasesTransactionException::class, CancellationException::class)
public suspend fun Purchases.awaitPurchase(
    packageToPurchase: Package,
    winBackOffer: WinBackOffer
): SuccessfulPurchase = suspendCoroutine { continuation ->
    purchase(
        packageToPurchase = packageToPurchase,
        winBackOffer = winBackOffer,
        onError = { error, userCancelled ->
            continuation.resumeWithException(PurchasesTransactionException(error, userCancelled))
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo))
        }
    )
}

/**
 * Restores purchases made with the current Store account for the current user. This method will
 * post all purchases associated with the current Store account to RevenueCat and become associated
 * with the current [appUserID][Purchases.appUserID]. If the receipt token is being used by an
 * existing user, the current [appUserID][Purchases.appUserID] will be aliased together with the
 * [appUserID][Purchases.appUserID] of the existing user. Going forward, either
 * [appUserID][Purchases.appUserID] will be able to reference the same user.
 *
 * You shouldn't use this method if you have your own account system. In that case "restoration" is
 * provided by your app passing the same [Purchases.appUserID] used to purchase originally.
 *
 * @throws PurchasesException in case of an error.
 */
@Throws(PurchasesException::class, CancellationException::class)
public suspend fun Purchases.awaitRestore(): CustomerInfo = suspendCoroutine { continuation ->
    restorePurchases(
        onError = { continuation.resumeWithException(PurchasesException(it)) },
        onSuccess = { continuation.resume(it) },
    )
}

/**
 * This function will change the current [Purchases.appUserID]. Typically this would be used after
 * a log out to identify a new user without calling [Purchases.configure].
 * @param newAppUserID The new appUserID that should be linked to the currently user
 *
 * @throws PurchasesException in case of an error.
 */
@Throws(PurchasesException::class, CancellationException::class)
public suspend fun Purchases.awaitLogIn(
    newAppUserID: String,
): SuccessfulLogin = suspendCoroutine { continuation ->
    logIn(
        newAppUserID = newAppUserID,
        onError = { continuation.resumeWithException(PurchasesException(it)) },
        onSuccess = { customerInfo, created ->
            continuation.resume(SuccessfulLogin(customerInfo, created))
        },
    )
}

/**
 * Resets the Purchases client clearing the save [Purchases.appUserID]. This will generate a random
 * user id and save it in the cache.
 *
 * @throws PurchasesException in case of an error.
 */
@Throws(PurchasesException::class, CancellationException::class)
public suspend fun Purchases.awaitLogOut(): CustomerInfo = suspendCoroutine { continuation ->
    logOut(
        onError = { continuation.resumeWithException(PurchasesException(it)) },
        onSuccess = { continuation.resume(it) },
    )
}

/**
 * Get the latest available customer info.
 *
 * @param fetchPolicy Specifies cache behavior for customer info retrieval.
 *
 * @throws PurchasesException in case of an error.
 */
@Throws(PurchasesException::class, CancellationException::class)
public suspend fun Purchases.awaitCustomerInfo(
    fetchPolicy: CacheFetchPolicy = CacheFetchPolicy.default(),
): CustomerInfo = suspendCoroutine { continuation ->
    getCustomerInfo(
        fetchPolicy = fetchPolicy,
        onError = { continuation.resumeWithException(PurchasesException(it)) },
        onSuccess = { continuation.resume(it) },
    )
}

/**
 * Gets the user's current store [Storefront].
 *
 * Returns null if the information could not be obtained.
 */
public suspend fun Purchases.awaitStorefront(): Storefront? = suspendCoroutine { continuation ->
    getStorefront {
        continuation.resume(it)
    }
}

/**
 * Fetches the virtual currencies for the current subscriber.
 *
 * @throws PurchasesException in case of an error.
 */
@Throws(PurchasesException::class, CancellationException::class)
public suspend fun Purchases.awaitVirtualCurrencies(): VirtualCurrencies = suspendCoroutine { continuation ->
    getVirtualCurrencies(
        onError = { continuation.resumeWithException(PurchasesException(it)) },
        onSuccess = { continuation.resume(it) }
    )
}
