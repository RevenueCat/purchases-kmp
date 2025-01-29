package com.revenuecat.purchases.kmp.either

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.ktx.SuccessfulLogin
import com.revenuecat.purchases.kmp.ktx.SuccessfulPurchase
import com.revenuecat.purchases.kmp.ktx.awaitGetEligibleWinBackOffersForPackage
import com.revenuecat.purchases.kmp.ktx.awaitGetEligibleWinBackOffersForProduct
import com.revenuecat.purchases.kmp.ktx.awaitPromotionalOffer
import com.revenuecat.purchases.kmp.ktx.awaitPurchase
import com.revenuecat.purchases.kmp.models.CacheFetchPolicy
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.GoogleReplacementMode
import com.revenuecat.purchases.kmp.models.Offerings
import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.models.PromotionalOffer
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.PurchasesException
import com.revenuecat.purchases.kmp.models.PurchasesTransactionException
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.models.WinBackOffer
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * The result of a failed purchase operation. Used by suspending functions returning [Either].
 */
public class FailedPurchase internal constructor(
    /**
     * The error that occurred.
     */
    public val error: PurchasesError,
    /**
     * Whether the user cancelled the transaction.
     */
    public val userCancelled: Boolean,
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
 * @return An [Either.Right] containing [CustomerInfo] if successful, an [Either.Left] containing a
 * [PurchasesError] in case of a failure.
 */
public suspend fun Purchases.awaitSyncPurchasesEither(): Either<PurchasesError, CustomerInfo> =
    suspendCoroutine { continuation ->
        syncPurchases(
            onError = { continuation.resume(it.left()) },
            onSuccess = { continuation.resume(it.right()) }
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
 * @return An [Either.Right] containing [Offerings] if successful, an [Either.Left] containing a
 * [PurchasesError] in case of a failure.
 */
public suspend fun Purchases.awaitSyncAttributesAndOfferingsIfNeededEither(): Either<PurchasesError, Offerings> =
    suspendCoroutine { continuation ->
        syncAttributesAndOfferingsIfNeeded(
            onError = { continuation.resume(it.left()) },
            onSuccess = { continuation.resume(it.right()) }
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
 * @return An [Either.Right] containing [Offerings] if successful, an [Either.Left] containing a
 * [PurchasesError] in case of a failure.
 */
public suspend fun Purchases.awaitOfferingsEither(): Either<PurchasesError, Offerings> =
    suspendCoroutine { continuation ->
        getOfferings(
            onError = { continuation.resume(it.left()) },
            onSuccess = { continuation.resume(it.right()) }
        )
    }

/**
 * Gets the [StoreProduct]s for the given list of product ids for all product types.
 *
 * @return An [Either.Right] containing a list of [StoreProduct]s if successful, an [Either.Left]
 * containing a [PurchasesError] in case of a failure.
 */
public suspend fun Purchases.awaitGetProductsEither(
    productIds: List<String>
): Either<PurchasesError, List<StoreProduct>> = suspendCoroutine { continuation ->
    getProducts(
        productIds = productIds,
        onError = { continuation.resume(it.left()) },
        onSuccess = { continuation.resume(it.right()) }
    )
}

/**
 * App Store only. Use this method to fetch a [PromotionalOffer] to use with [awaitPurchase].
 *
 * @return An [Either.Right] containing a [PromotionalOffer] if successful, an [Either.Left]
 * containing a [PurchasesError] in case of a failure.
 */
public suspend fun Purchases.awaitPromotionalOfferEither(
    discount: StoreProductDiscount,
    storeProduct: StoreProduct,
): Either<PurchasesError, PromotionalOffer> = suspendCoroutine { continuation ->
    getPromotionalOffer(
        discount = discount,
        storeProduct = storeProduct,
        onError = { continuation.resume(it.left()) },
        onSuccess = { continuation.resume(it.right()) }
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
 * @return An [Either.Right] containing a [SuccessfulPurchase] if successful, an [Either.Left]
 * containing a [FailedPurchase] in case of a failure.
 */
public suspend fun Purchases.awaitPurchaseEither(
    storeProduct: StoreProduct,
    isPersonalizedPrice: Boolean? = null,
    oldProductId: String? = null,
    replacementMode: GoogleReplacementMode = GoogleReplacementMode.WITHOUT_PRORATION,
): Either<FailedPurchase, SuccessfulPurchase> = suspendCoroutine { continuation ->
    purchase(
        storeProduct = storeProduct,
        onError = { error, userCancelled ->
            continuation.resume(FailedPurchase(error, userCancelled).left())
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo).right())
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
 * @return An [Either.Right] containing a [SuccessfulPurchase] if successful, an [Either.Left]
 * containing a [FailedPurchase] in case of a failure.
 */
public suspend fun Purchases.awaitPurchaseEither(
    packageToPurchase: Package,
    isPersonalizedPrice: Boolean? = null,
    oldProductId: String? = null,
    replacementMode: GoogleReplacementMode = GoogleReplacementMode.WITHOUT_PRORATION,
): Either<FailedPurchase, SuccessfulPurchase> = suspendCoroutine { continuation ->
    purchase(
        packageToPurchase = packageToPurchase,
        onError = { error, userCancelled ->
            continuation.resume(FailedPurchase(error, userCancelled).left())
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo).right())
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
 * @return An [Either.Right] containing a [SuccessfulPurchase] if successful, an [Either.Left]
 * containing a [FailedPurchase] in case of a failure.
 */
public suspend fun Purchases.awaitPurchaseEither(
    subscriptionOption: SubscriptionOption,
    isPersonalizedPrice: Boolean? = null,
    oldProductId: String? = null,
    replacementMode: GoogleReplacementMode = GoogleReplacementMode.WITHOUT_PRORATION,
): Either<FailedPurchase, SuccessfulPurchase> = suspendCoroutine { continuation ->
    purchase(
        subscriptionOption = subscriptionOption,
        onError = { error, userCancelled ->
            continuation.resume(FailedPurchase(error, userCancelled).left())
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo).right())
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
 * @return An [Either.Right] containing a [SuccessfulPurchase] if successful, an [Either.Left]
 * containing a [FailedPurchase] in case of a failure.
 *
 * @see [awaitPromotionalOffer]
 */
public suspend fun Purchases.awaitPurchaseEither(
    storeProduct: StoreProduct,
    promotionalOffer: PromotionalOffer,
): Either<FailedPurchase, SuccessfulPurchase> = suspendCoroutine { continuation ->
    purchase(
        storeProduct = storeProduct,
        promotionalOffer = promotionalOffer,
        onError = { error, userCancelled ->
            continuation.resume(FailedPurchase(error, userCancelled).left())
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo).right())
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
 * @return An [Either.Right] containing a [SuccessfulPurchase] if successful, an [Either.Left]
 * containing a [FailedPurchase] in case of a failure.
 *
 * @see [awaitPromotionalOffer]
 */
public suspend fun Purchases.awaitPurchaseEither(
    packageToPurchase: Package,
    promotionalOffer: PromotionalOffer,
): Either<FailedPurchase, SuccessfulPurchase> = suspendCoroutine { continuation ->
    purchase(
        packageToPurchase = packageToPurchase,
        promotionalOffer = promotionalOffer,
        onError = { error, userCancelled ->
            continuation.resume(FailedPurchase(error, userCancelled).left())
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo).right())
        },
    )
}

/**
 * Fetches the win-back offers that a subscriber is eligible for on a given product.
 *
 * iOS only. Requires iOS 18.0+ and StoreKit 2 and returns an [Either] with an
 * error if these requirements aren't met.
 *
 * @param storeProduct: The product to check for eligible win-back offers on.
 *
 * @return An [Either.Right] containing a [List] of [WinBackOffer]s if successful,
 * an [Either.Left] containing a [PurchasesError] in case of a failure.
 */
public suspend fun Purchases.awaitGetEligibleWinBackOffersForProductEither(
    storeProduct: StoreProduct
): Either<PurchasesError, List<WinBackOffer>> = suspendCoroutine { continuation ->
    getEligibleWinBackOffersForProduct(
        storeProduct = storeProduct,
        onError = { error ->
            continuation.resume(error.left())
        },
        onSuccess = { eligibleWinBackOffers ->
            continuation.resume(eligibleWinBackOffers.right())
        }
    )
}

/**
 * Fetches the win-back offers that a subscriber is eligible for on a given package.
 *
 * iOS only. Requires iOS 18.0+ and StoreKit 2 and returns an [Either] with an
 * error if these requirements aren't met.
 *
 * @param packageToCheck: The package to check for eligible win-back offers on.
 *
 * @return An [Either.Right] containing a [List] of [WinBackOffer]s if successful,
 * an [Either.Left] containing a [PurchasesError] in case of a failure.
 */
public suspend fun Purchases.awaitGetEligibleWinBackOffersForPackageEither(
    packageToCheck: Package,
): Either<PurchasesError, List<WinBackOffer>> = suspendCoroutine { continuation ->
    getEligibleWinBackOffersForPackage(
        packageToCheck = packageToCheck,
        onError = { error ->
            continuation.resume(error.left())
        },
        onSuccess = { eligibleWinBackOffers ->
            continuation.resume(eligibleWinBackOffers.right())
        }
    )
}

/**
 * Purchases a product with a given win-back offer. If you are using the Offerings system, use the
 * overload with a [Package] parameter instead.
 *
 * iOS only. Requires iOS 18.0+ and StoreKit 2 and returns an [Either] with an
 * error if these requirements aren't met.
 *
 * @param storeProduct: The product to purchase
 * @param winBackOffer: The win-back offer to apply to the purchase
 *
 * @return An [Either.Right] containing a [SuccessfulPurchase],
 * an [Either.Left] containing a [FailedPurchase] in case of a failure.
 *
 * @see [awaitGetEligibleWinBackOffersForProductEither]
 */
public suspend fun Purchases.awaitPurchaseEither(
    storeProduct: StoreProduct,
    winBackOffer: WinBackOffer
): Either<FailedPurchase, SuccessfulPurchase> = suspendCoroutine { continuation ->
    purchase(
        storeProduct = storeProduct,
        winBackOffer = winBackOffer,
        onError = { error, userCancelled ->
            continuation.resume(FailedPurchase(error, userCancelled).left())
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo).right())
        }
    )
}

/**
 * Purchases a package with a given win-back offer.
 *
 * iOS only. Requires iOS 18.0+ and StoreKit 2 and returns an [Either] with an
 * error if these requirements aren't met.
 *
 * @param packageToPurchase: The package to purchase
 * @param winBackOffer: The win-back offer to apply to the purchase
 *
 * @return An [Either.Right] containing a [SuccessfulPurchase],
 * an [Either.Left] containing a [FailedPurchase] in case of a failure.
 *
 * @see [awaitGetEligibleWinBackOffersForPackageEither]
 */
public suspend fun Purchases.awaitPurchaseEither(
    packageToPurchase: Package,
    winBackOffer: WinBackOffer
): Either<FailedPurchase, SuccessfulPurchase> = suspendCoroutine { continuation ->
    purchase(
        packageToPurchase = packageToPurchase,
        winBackOffer = winBackOffer,
        onError = { error, userCancelled ->
            continuation.resume(FailedPurchase(error, userCancelled).left())
        },
        onSuccess = { storeTransaction, customerInfo ->
            continuation.resume(SuccessfulPurchase(storeTransaction, customerInfo).right())
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
 * provided by your app passing the same [appUserID][Purchases.appUserID] used to purchase
 * originally.
 *
 * @return An [Either.Right] containing [CustomerInfo] if successful, an [Either.Left] containing a
 * [PurchasesError] in case of a failure.
 */
public suspend fun Purchases.awaitRestoreEither(): Either<PurchasesError, CustomerInfo> =
    suspendCoroutine { continuation ->
        restorePurchases(
            onError = { continuation.resume(it.left()) },
            onSuccess = { continuation.resume(it.right()) },
        )
    }

/**
 * This function will change the current [appUserID][Purchases.appUserID]. Typically this would be
 * used after a log out to identify a new user without calling [Purchases.configure].
 * @param newAppUserID The new appUserID that should be linked to the currently user
 *
 * @return An [Either.Right] containing [CustomerInfo] if successful, an [Either.Left] containing a
 * [PurchasesError] in case of a failure.
 */
public suspend fun Purchases.awaitLogInEither(
    newAppUserID: String,
): Either<PurchasesError, SuccessfulLogin> = suspendCoroutine { continuation ->
    logIn(
        newAppUserID = newAppUserID,
        onError = { continuation.resume(it.left()) },
        onSuccess = { customerInfo, created ->
            continuation.resume(SuccessfulLogin(customerInfo, created).right())
        },
    )
}

/**
 * Resets the Purchases client clearing the save [appUserID][Purchases.appUserID]. This will
 * generate a random user id and save it in the cache.
 *
 * @return An [Either.Right] containing [CustomerInfo] if successful, an [Either.Left] containing a
 * [PurchasesError] in case of a failure.
 */
public suspend fun Purchases.awaitLogOutEither(): Either<PurchasesError, CustomerInfo> =
    suspendCoroutine { continuation ->
        logOut(
            onError = { continuation.resume(it.left()) },
            onSuccess = { continuation.resume(it.right()) },
        )
    }

/**
 * Get the latest available customer info.
 *
 * @param fetchPolicy Specifies cache behavior for customer info retrieval.
 *
 * @return An [Either.Right] containing [CustomerInfo] if successful, an [Either.Left] containing a
 * [PurchasesError] in case of a failure.
 */
public suspend fun Purchases.awaitCustomerInfoEither(
    fetchPolicy: CacheFetchPolicy = CacheFetchPolicy.default(),
): Either<PurchasesError, CustomerInfo> = suspendCoroutine { continuation ->
    getCustomerInfo(
        fetchPolicy = fetchPolicy,
        onError = { continuation.resume(it.left()) },
        onSuccess = { continuation.resume(it.right()) },
    )
}
