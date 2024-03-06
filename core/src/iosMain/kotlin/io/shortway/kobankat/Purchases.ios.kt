@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package io.shortway.kobankat

import cocoapods.RevenueCat.RCPurchases
import cocoapods.RevenueCat.RCStoreProduct
import cocoapods.RevenueCat.setAd
import cocoapods.RevenueCat.setAdGroup
import cocoapods.RevenueCat.setAdjustID
import cocoapods.RevenueCat.setAirshipChannelID
import cocoapods.RevenueCat.setAppsflyerID
import cocoapods.RevenueCat.setAttributes
import cocoapods.RevenueCat.setCampaign
import cocoapods.RevenueCat.setCleverTapID
import cocoapods.RevenueCat.setCreative
import cocoapods.RevenueCat.setDisplayName
import cocoapods.RevenueCat.setEmail
import cocoapods.RevenueCat.setFBAnonymousID
import cocoapods.RevenueCat.setFirebaseAppInstanceID
import cocoapods.RevenueCat.setKeyword
import cocoapods.RevenueCat.setMediaSource
import cocoapods.RevenueCat.setMixpanelDistinctID
import cocoapods.RevenueCat.setMparticleID
import cocoapods.RevenueCat.setOnesignalID
import cocoapods.RevenueCat.setOnesignalUserID
import cocoapods.RevenueCat.setPhoneNumber
import cocoapods.RevenueCat.setPushTokenString
import io.shortway.kobankat.models.GoogleReplacementMode
import io.shortway.kobankat.models.PromotionalOffer
import io.shortway.kobankat.models.StoreProduct
import io.shortway.kobankat.models.StoreProductDiscount
import io.shortway.kobankat.models.StoreTransaction
import io.shortway.kobankat.models.SubscriptionOption

public actual typealias Purchases = RCPurchases

public actual var Purchases.finishTransactions: Boolean
    get() = finishTransactions()
    set(value) {
        setFinishTransactions(value)
    }

public actual val Purchases.appUserID: String
    get() = appUserID()

public actual var Purchases.delegate: PurchasesDelegate?
    get() = delegate()?.toPurchasesDelegate()
    set(value) = setDelegate(value?.toRcPurchasesDelegate())

public actual val Purchases.isAnonymous: Boolean
    get() = isAnonymous()

public actual val Purchases.store: Store
    get() = Store.APP_STORE

public actual fun Purchases.syncPurchases(
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (CustomerInfo) -> Unit,
): Unit = syncPurchasesWithCompletionHandler { customerInfo, error ->
    if (error != null) onError(error.toPurchasesErrorOrThrow())
    else onSuccess(customerInfo ?: error("Expected a non-null RCCustomerInfo"))
}

public actual fun Purchases.syncObserverModeAmazonPurchase(
    productID: String,
    receiptID: String,
    amazonUserID: String,
    isoCurrencyCode: String?,
    price: Double?,
) {
    // No-op on iOS
}

public actual fun Purchases.syncAttributesAndOfferingsIfNeeded(
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (offerings: Offerings) -> Unit,
): Unit = syncAttributesAndOfferingsIfNeededWithCompletion { offerings, error ->
    if (error != null) onError(error.toPurchasesErrorOrThrow())
    else onSuccess(offerings ?: error("Expected a non-null RCOfferings"))
}

public actual fun Purchases.getOfferings(
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (offerings: Offerings) -> Unit,
): Unit = getOfferingsWithCompletion { offerings, error ->
    if (error != null) onError(error.toPurchasesErrorOrThrow())
    else onSuccess(offerings ?: error("Expected a non-null RCOfferings"))
}

public actual fun Purchases.getProducts(
    productIds: List<String>,
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (storeProducts: List<StoreProduct>) -> Unit,
): Unit = getProductsWithIdentifiers(
    productIdentifiers = productIds,
    completion = {
        onSuccess(it.orEmpty().map { product -> (product as RCStoreProduct) })
    },
)

public actual fun Purchases.getPromotionalOffer(
    discount: StoreProductDiscount,
    storeProduct: StoreProduct,
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (offer: PromotionalOffer) -> Unit,
): Unit = getPromotionalOfferForProductDiscount(
    discount = discount,
    withProduct = storeProduct
) { offer, error ->
    if (error != null) onError(error.toPurchasesErrorOrThrow())
    else onSuccess(offer ?: error("Expected a non-null RCPromotionalOffer"))
}

public actual fun Purchases.purchase(
    storeProduct: StoreProduct,
    onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
    onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    isPersonalizedPrice: Boolean?,
    oldProductId: String?,
    replacementMode: GoogleReplacementMode,
): Unit = purchaseProduct(storeProduct) { transaction, customerInfo, error, userCancelled ->
    if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
    else onSuccess(
        transaction ?: error("Expected a non-null RCStoreTransaction"),
        customerInfo ?: error("Expected a non-null RCCustomerInfo")
    )
}

public actual fun Purchases.purchase(
    packageToPurchase: Package,
    onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
    onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    isPersonalizedPrice: Boolean?,
    oldProductId: String?,
    replacementMode: GoogleReplacementMode,
): Unit = purchasePackage(packageToPurchase) { transaction, customerInfo, error, userCancelled ->
    if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
    else onSuccess(
        transaction ?: error("Expected a non-null RCStoreTransaction"),
        customerInfo ?: error("Expected a non-null RCCustomerInfo")
    )
}

public actual fun Purchases.purchase(
    subscriptionOption: SubscriptionOption,
    onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
    onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    isPersonalizedPrice: Boolean?,
    oldProductId: String?,
    replacementMode: GoogleReplacementMode,
): Unit = error(
    "Purchasing a SubscriptionOption is not possible on iOS. " +
            "Did you mean Purchases.purchase(StoreProduct, PromotionalOffer) or " +
            "Purchases.purchase(Package, PromotionalOffer)?"
)

public actual fun Purchases.purchase(
    storeProduct: StoreProduct,
    promotionalOffer: PromotionalOffer,
    onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
    onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
): Unit = purchaseProduct(
    storeProduct,
    promotionalOffer
) { transaction, customerInfo, error, userCancelled ->
    if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
    else onSuccess(
        transaction ?: error("Expected a non-null RCStoreTransaction"),
        customerInfo ?: error("Expected a non-null RCCustomerInfo")
    )
}

public actual fun Purchases.purchase(
    packageToPurchase: Package,
    promotionalOffer: PromotionalOffer,
    onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
    onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
): Unit = purchasePackage(
    packageToPurchase,
    promotionalOffer
) { transaction, customerInfo, error, userCancelled ->
    if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
    else onSuccess(
        transaction ?: error("Expected a non-null RCStoreTransaction"),
        customerInfo ?: error("Expected a non-null RCCustomerInfo")
    )
}

public actual fun Purchases.restorePurchases(
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (customerInfo: CustomerInfo) -> Unit,
): Unit = restorePurchasesWithCompletion { customerInfo, error ->
    if (error != null) onError(error.toPurchasesErrorOrThrow())
    else onSuccess(customerInfo ?: error("Expected a non-null RCCustomerInfo"))
}

public actual fun Purchases.logIn(
    newAppUserID: String,
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (customerInfo: CustomerInfo, created: Boolean) -> Unit
): Unit = logIn(
    appUserID = newAppUserID,
    completion = { customerInfo, created, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(customerInfo ?: error("Expected a non-null RCCustomerInfo"), created)
    }
)

public actual fun Purchases.logOut(
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (customerInfo: CustomerInfo) -> Unit,
): Unit = logOutWithCompletion { customerInfo, error ->
    if (error != null) onError(error.toPurchasesErrorOrThrow())
    else onSuccess(customerInfo ?: error("Expected a non-null RCCustomerInfo"))
}

public actual fun Purchases.close() {
    setDelegate(null)
}

public actual fun Purchases.getCustomerInfo(
    fetchPolicy: CacheFetchPolicy,
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (customerInfo: CustomerInfo) -> Unit,
): Unit = getCustomerInfoWithFetchPolicy(
    fetchPolicy.toRCCacheFetchPolicy()
) { customerInfo, error ->
    if (error != null) onError(error.toPurchasesErrorOrThrow())
    else onSuccess(customerInfo ?: error("Expected a non-null RCCustomerInfo"))
}

public actual fun Purchases.invalidateCustomerInfoCache(): Unit =
    invalidateCustomerInfoCache()

public actual fun Purchases.setAttributes(attributes: Map<String, String?>): Unit =
    attribution().setAttributes(attributes.mapKeys { (key, _) -> key })

public actual fun Purchases.setEmail(email: String?): Unit =
    attribution().setEmail(email)

public actual fun Purchases.setPhoneNumber(phoneNumber: String?): Unit =
    attribution().setPhoneNumber(phoneNumber)

public actual fun Purchases.setDisplayName(displayName: String?): Unit =
    attribution().setDisplayName(displayName)

public actual fun Purchases.setPushToken(fcmToken: String?): Unit =
    attribution().setPushTokenString(fcmToken)

public actual fun Purchases.setMixpanelDistinctID(mixpanelDistinctID: String?): Unit =
    attribution().setMixpanelDistinctID(mixpanelDistinctID)

public actual fun Purchases.setOnesignalID(onesignalID: String?): Unit =
    attribution().setOnesignalID(onesignalID)

public actual fun Purchases.setOnesignalUserID(onesignalUserID: String?): Unit =
    attribution().setOnesignalUserID(onesignalUserID)

public actual fun Purchases.setAirshipChannelID(airshipChannelID: String?): Unit =
    attribution().setAirshipChannelID(airshipChannelID)

public actual fun Purchases.setFirebaseAppInstanceID(firebaseAppInstanceID: String?): Unit =
    attribution().setFirebaseAppInstanceID(firebaseAppInstanceID)

public actual fun Purchases.collectDeviceIdentifiers(): Unit =
    collectDeviceIdentifiers()

public actual fun Purchases.setAdjustID(adjustID: String?): Unit =
    attribution().setAdjustID(adjustID)

public actual fun Purchases.setAppsflyerID(appsflyerID: String?): Unit =
    attribution().setAppsflyerID(appsflyerID)

public actual fun Purchases.setFBAnonymousID(fbAnonymousID: String?): Unit =
    attribution().setFBAnonymousID(fbAnonymousID)

public actual fun Purchases.setMparticleID(mparticleID: String?): Unit =
    attribution().setMparticleID(mparticleID)

public actual fun Purchases.setCleverTapID(cleverTapID: String?): Unit =
    attribution().setCleverTapID(cleverTapID)

public actual fun Purchases.setMediaSource(mediaSource: String?): Unit =
    attribution().setMediaSource(mediaSource)

public actual fun Purchases.setCampaign(campaign: String?): Unit =
    attribution().setCampaign(campaign)

public actual fun Purchases.setAdGroup(adGroup: String?): Unit =
    attribution().setAdGroup(adGroup)

public actual fun Purchases.setAd(ad: String?): Unit =
    attribution().setAd(ad)

public actual fun Purchases.setKeyword(keyword: String?): Unit =
    attribution().setKeyword(keyword)

public actual fun Purchases.setCreative(creative: String?): Unit =
    attribution().setCreative(creative)
