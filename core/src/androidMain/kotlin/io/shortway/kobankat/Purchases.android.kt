@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package io.shortway.kobankat

import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.getCustomerInfoWith
import com.revenuecat.purchases.getOfferingsWith
import com.revenuecat.purchases.getProductsWith
import com.revenuecat.purchases.logInWith
import com.revenuecat.purchases.logOutWith
import com.revenuecat.purchases.models.InAppMessageType
import com.revenuecat.purchases.purchaseWith
import com.revenuecat.purchases.restorePurchasesWith
import com.revenuecat.purchases.syncAttributesAndOfferingsIfNeededWith
import com.revenuecat.purchases.syncPurchasesWith
import io.shortway.kobankat.di.AndroidProvider
import io.shortway.kobankat.di.currentOrThrow
import io.shortway.kobankat.models.GoogleReplacementMode
import io.shortway.kobankat.models.PromotionalOffer
import io.shortway.kobankat.models.StoreMessageType
import io.shortway.kobankat.models.StoreProduct
import io.shortway.kobankat.models.StoreProductDiscount
import io.shortway.kobankat.models.StoreTransaction
import io.shortway.kobankat.models.SubscriptionOption
import com.revenuecat.purchases.Purchases as RcPurchases

public actual typealias Purchases = RcPurchases

public actual var Purchases.finishTransactions: Boolean
    get() = finishTransactions
    set(value) {
        finishTransactions = value
    }

public actual val Purchases.appUserID: String
    get() = appUserID

public actual var Purchases.delegate: PurchasesDelegate?
    get() = updatedCustomerInfoListener?.toPurchasesDelegate()
    set(value) {
        updatedCustomerInfoListener = value?.toUpdatedCustomerInfoListener()
    }

public actual val Purchases.isAnonymous: Boolean
    get() = isAnonymous

public actual val Purchases.store: Store
    get() = store

public actual fun Purchases.syncPurchases(
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (CustomerInfo) -> Unit,
): Unit = syncPurchasesWith(
    onError = { onError(it.toPurchasesError()) },
    onSuccess = { onSuccess(it) },
)

public actual fun Purchases.syncObserverModeAmazonPurchase(
    productID: String,
    receiptID: String,
    amazonUserID: String,
    isoCurrencyCode: String?,
    price: Double?,
): Unit = syncObserverModeAmazonPurchase(
    productID = productID,
    receiptID = receiptID,
    amazonUserID = amazonUserID,
    isoCurrencyCode = isoCurrencyCode,
    price = price
)

public actual fun Purchases.syncAttributesAndOfferingsIfNeeded(
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (offerings: Offerings) -> Unit,
): Unit = syncAttributesAndOfferingsIfNeededWith(
    onError = { error -> onError(error.toPurchasesError()) },
    onSuccess = onSuccess
)

public actual fun Purchases.getOfferings(
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (offerings: Offerings) -> Unit,
): Unit = getOfferingsWith(
    onError = { error -> onError(error.toPurchasesError()) },
    onSuccess = { offerings -> onSuccess(offerings) }
)

public actual fun Purchases.getProducts(
    productIds: List<String>,
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (storeProducts: List<StoreProduct>) -> Unit,
): Unit = getProductsWith(
    productIds = productIds,
    onError = { onError(it.toPurchasesError()) },
    onGetStoreProducts = { onSuccess(it.map { product -> StoreProduct(product) }) },
)

public actual fun Purchases.getPromotionalOffer(
    discount: StoreProductDiscount,
    storeProduct: StoreProduct,
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (offer: PromotionalOffer) -> Unit,
): Unit = error(
    "Getting promotional offers is not possible on Android. " +
            "Did you mean StoreProduct.subscriptionOptions?"
)

public actual fun Purchases.purchase(
    storeProduct: StoreProduct,
    onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
    onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    isPersonalizedPrice: Boolean?,
    oldProductId: String?,
    replacementMode: GoogleReplacementMode,
): Unit = purchaseWith(
    purchaseParams = PurchaseParams.Builder(
        AndroidProvider.currentOrThrow(),
        storeProduct
    ).apply {
        if (isPersonalizedPrice != null) isPersonalizedPrice(isPersonalizedPrice)
        if (oldProductId != null) oldProductId(oldProductId)
    }.googleReplacementMode(replacementMode)
        .build(),
    onError = { error, userCancelled -> onError(error.toPurchasesError(), userCancelled) },
    onSuccess = { purchase, customerInfo -> onSuccess(purchase!!, customerInfo) },
)

public actual fun Purchases.purchase(
    packageToPurchase: Package,
    onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
    onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    isPersonalizedPrice: Boolean?,
    oldProductId: String?,
    replacementMode: GoogleReplacementMode,
): Unit = purchaseWith(
    purchaseParams = PurchaseParams.Builder(
        AndroidProvider.currentOrThrow(),
        packageToPurchase
    ).apply {
        if (isPersonalizedPrice != null) isPersonalizedPrice(isPersonalizedPrice)
        if (oldProductId != null) oldProductId(oldProductId)
    }.googleReplacementMode(replacementMode)
        .build(),
    onError = { error, userCancelled -> onError(error.toPurchasesError(), userCancelled) },
    onSuccess = { purchase, customerInfo -> onSuccess(purchase!!, customerInfo) },
)

public actual fun Purchases.purchase(
    subscriptionOption: SubscriptionOption,
    onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
    onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    isPersonalizedPrice: Boolean?,
    oldProductId: String?,
    replacementMode: GoogleReplacementMode,
): Unit = purchaseWith(
    purchaseParams = PurchaseParams.Builder(
        AndroidProvider.currentOrThrow(),
        subscriptionOption
    ).apply {
        if (isPersonalizedPrice != null) isPersonalizedPrice(isPersonalizedPrice)
        if (oldProductId != null) oldProductId(oldProductId)
    }.googleReplacementMode(replacementMode)
        .build(),
    onError = { error, userCancelled -> onError(error.toPurchasesError(), userCancelled) },
    onSuccess = { purchase, customerInfo -> onSuccess(purchase!!, customerInfo) },
)

public actual fun Purchases.purchase(
    storeProduct: StoreProduct,
    promotionalOffer: PromotionalOffer,
    onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
    onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
): Unit = error(
    "Purchasing a StoreProduct with a PromotionalOffer is not possible on Android. " +
            "Did you mean Purchases.purchase(SubscriptionOption)?"
)

public actual fun Purchases.purchase(
    packageToPurchase: Package,
    promotionalOffer: PromotionalOffer,
    onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
    onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
): Unit = error(
    "Purchasing a Package with a PromotionalOffer is not possible on Android. " +
            "Did you mean Purchases.purchase(SubscriptionOption)?"
)

public actual fun Purchases.restorePurchases(
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (customerInfo: CustomerInfo) -> Unit,
): Unit = restorePurchasesWith(
    onError = { onError(it.toPurchasesError()) },
    onSuccess = { onSuccess(it) },
)

public actual fun Purchases.logIn(
    newAppUserID: String,
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (customerInfo: CustomerInfo, created: Boolean) -> Unit
): Unit = logInWith(
    appUserID = newAppUserID,
    onError = { onError(it.toPurchasesError()) },
    onSuccess = { customerInfo, created -> onSuccess(customerInfo, created) }
)

public actual fun Purchases.logOut(
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (customerInfo: CustomerInfo) -> Unit,
): Unit = logOutWith(
    onError = { onError(it.toPurchasesError()) },
    onSuccess = { onSuccess(it) },
)

public actual fun Purchases.close(): Unit =
    close()

public actual fun Purchases.getCustomerInfo(
    fetchPolicy: CacheFetchPolicy,
    onError: (error: PurchasesError) -> Unit,
    onSuccess: (customerInfo: CustomerInfo) -> Unit,
): Unit = getCustomerInfoWith(
    fetchPolicy = fetchPolicy,
    onError = { onError(it.toPurchasesError()) },
    onSuccess = { onSuccess(it) }
)

public actual fun Purchases.showInAppMessagesIfNeeded(
    messageTypes: List<StoreMessageType>,
): Unit = showInAppMessagesIfNeeded(
    activity = AndroidProvider.currentOrThrow(),
    inAppMessageTypes = messageTypes.mapNotNull { it.toInAppMessageTypeOrNull() }
)

public actual fun Purchases.invalidateCustomerInfoCache(): Unit =
    invalidateCustomerInfoCache()

public actual fun Purchases.setAttributes(attributes: Map<String, String?>): Unit =
    setAttributes(attributes)

public actual fun Purchases.setEmail(email: String?): Unit =
    setEmail(email)

public actual fun Purchases.setPhoneNumber(phoneNumber: String?): Unit =
    setPhoneNumber(phoneNumber)

public actual fun Purchases.setDisplayName(displayName: String?): Unit =
    setDisplayName(displayName)

public actual fun Purchases.setPushToken(fcmToken: String?): Unit =
    setPushToken(fcmToken)

public actual fun Purchases.setMixpanelDistinctID(mixpanelDistinctID: String?): Unit =
    setMixpanelDistinctID(mixpanelDistinctID)

public actual fun Purchases.setOnesignalID(onesignalID: String?): Unit =
    setOnesignalID(onesignalID)

public actual fun Purchases.setOnesignalUserID(onesignalUserID: String?): Unit =
    setOnesignalUserID(onesignalUserID)

public actual fun Purchases.setAirshipChannelID(airshipChannelID: String?): Unit =
    setAirshipChannelID(airshipChannelID)

public actual fun Purchases.setFirebaseAppInstanceID(firebaseAppInstanceID: String?): Unit =
    setFirebaseAppInstanceID(firebaseAppInstanceID)

public actual fun Purchases.collectDeviceIdentifiers(): Unit =
    collectDeviceIdentifiers()

public actual fun Purchases.setAdjustID(adjustID: String?): Unit =
    setAdjustID(adjustID)

public actual fun Purchases.setAppsflyerID(appsflyerID: String?): Unit =
    setAppsflyerID(appsflyerID)

public actual fun Purchases.setFBAnonymousID(fbAnonymousID: String?): Unit =
    setFBAnonymousID(fbAnonymousID)

public actual fun Purchases.setMparticleID(mparticleID: String?): Unit =
    setMparticleID(mparticleID)

public actual fun Purchases.setCleverTapID(cleverTapID: String?): Unit =
    setCleverTapID(cleverTapID)

public actual fun Purchases.setMediaSource(mediaSource: String?): Unit =
    setMediaSource(mediaSource)

public actual fun Purchases.setCampaign(campaign: String?): Unit =
    setCampaign(campaign)

public actual fun Purchases.setAdGroup(adGroup: String?): Unit =
    setAdGroup(adGroup)

public actual fun Purchases.setAd(ad: String?): Unit =
    setAd(ad)

public actual fun Purchases.setKeyword(keyword: String?): Unit =
    setKeyword(keyword)

public actual fun Purchases.setCreative(creative: String?): Unit =
    setCreative(creative)

private fun StoreMessageType.toInAppMessageTypeOrNull(): InAppMessageType? =
    when (this) {
        StoreMessageType.BILLING_ISSUES -> InAppMessageType.BILLING_ISSUES
        StoreMessageType.GENERIC,
        StoreMessageType.PRICE_INCREASE_CONSENT -> null
    }
